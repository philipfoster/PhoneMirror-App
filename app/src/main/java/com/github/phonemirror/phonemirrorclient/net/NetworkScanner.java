package com.github.phonemirror.phonemirrorclient.net;

import android.os.Build;

import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.net.message.Message;
import com.github.phonemirror.phonemirrorclient.net.message.MessageType;
import com.github.phonemirror.phonemirrorclient.net.transport.TransportLayer;
import com.github.phonemirror.phonemirrorclient.net.transport.TransportListener;
import com.github.phonemirror.phonemirrorclient.repo.SerialRepository;
import com.github.phonemirror.phonemirrorclient.util.Configuration;
import com.google.gson.Gson;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import io.reactivex.internal.subscriptions.BooleanSubscription;
import timber.log.Timber;

/**
 * This class is responsible for searching the network for compatible devices, and publishing them
 * to a subscriber.
 */
public class NetworkScanner implements Publisher<Device> {

    private static final int WAIT_DURATION_MS = 3000;


    private final Configuration config;
    private final TransportLayer server;
    private final SerialRepository repo;
    private final Gson gson;
    private final AtomicBoolean isScanning = new AtomicBoolean(false);
    private ExecutorService threadPool;

    @Inject
    public NetworkScanner(Configuration config, TransportLayer server, SerialRepository repo, ExecutorService threadPool, Gson gson) {
        this.config = config;
        this.server = server;
        this.repo = repo;
        this.threadPool = threadPool;
        this.gson = gson;
    }

    @Override
    public void subscribe(Subscriber<? super Device> subscriber) {
        if (isScanning.compareAndSet(false, true)) {
            try {
                CountDownLatch latch = new CountDownLatch(2);
                BooleanSubscription subscription = new BooleanSubscription();
                subscriber.onSubscribe(subscription);

                threadPool.submit(() -> startAcknowledgementListener(subscriber, latch));
                threadPool.submit(() -> sendPresenceBeacon(subscriber, latch));
                latch.await();

                subscriber.onComplete();
            } catch (InterruptedException e) {
                Timber.e(e);
                subscriber.onError(e);
            } finally {
                isScanning.set(false);
            }
        }
    }

    private void sendPresenceBeacon(Subscriber<? super Device> subscriber, CountDownLatch latch) {
        Timber.v("Sending multicast beacon.");

        try (MulticastSocket socket = new MulticastSocket(config.getPort())) {
            socket.joinGroup(config.getMulticastGroup());

            //noinspection unchecked
            Message<String> msg = Message.<String>build()
                    .setType(MessageType.NETWORK_SCAN)
                    .setId(repo)
                    .setPayload(Build.MODEL + " " + Build.DEVICE)
                    .createMessage();

            byte[] buf = gson.toJson(msg, msg.getDataType()).getBytes("US-ASCII");

            DatagramPacket packet = new DatagramPacket(buf, buf.length, config.getMulticastGroup(), config.getPort());
            socket.send(packet);

        } catch (IOException e) {
            Timber.e("IOException occurred while sending presence beacon.", e);
            subscriber.onError(e);
        } finally {
            latch.countDown();
        }
    }

    /**
     * Listen for acknowledgements from the beacon (which should be sent after starting this listener
     * by calling {@link #sendPresenceBeacon(Subscriber, CountDownLatch)}. This method is blocking, so
     * it should be executed on a background thread.
     * @param subscriber        The subscriber which is interested in receiving updates about deviecs.
     * @param latch             A signal to the caller that this thread has completed for synchronization.
     */
    private void startAcknowledgementListener(Subscriber<? super Device> subscriber, CountDownLatch latch) {
        Timber.d("Listening for devices");

        TransportListener listener = object -> {
            //noinspection unchecked,UnnecessaryLocalVariable
            Message<String> msg = object;
            Device device = new Device.Builder()
                    .setSerialNo(object.getId())
                    .setIpAddress(msg.getRecipient())
                    .setName(msg.getPayload())
                    .setDeviceType(Device.Type.PHONE)
                    .build();

            subscriber.onNext(device);
        };

        server.registerListener(listener, msg -> msg.getMessageType() == MessageType.NETWORK_SCAN_ACK);
        try {
            Thread.sleep(WAIT_DURATION_MS);
        } catch (InterruptedException e) {
            Timber.e("Acknowledgement listener was interrupted while waiting for responses.", e);
        } finally {
            server.unregisterListener(listener);
            latch.countDown();
        }
    }
}
