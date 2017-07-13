package com.github.phonemirror.phonemirrorclient.net.transport;

import com.github.phonemirror.phonemirrorclient.net.message.Message;
import com.github.phonemirror.phonemirrorclient.util.Configuration;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import timber.log.Timber;

public class TcpServer extends TransportLayer {

    private static final int SOCKET_TIMEOUT_MS = 1000;

    private final Object runningLock = new Object();
    private Configuration config;
    private Gson gson;
    private volatile boolean isRunning = true;

    @Inject
    public TcpServer(ExecutorService threadPool, Configuration config, Gson gson) {
        this.config = config;
        this.gson = gson;

        threadPool.submit(this::startServer);
    }

    private void startServer() {
        try (ServerSocket server = new ServerSocket()) {
            server.setReuseAddress(true);
            server.setSoTimeout(SOCKET_TIMEOUT_MS);
            server.bind(new InetSocketAddress(config.getPort()));

            while (true) {
                synchronized (runningLock) {
                    if (!isRunning) {
                        // stop the server
                        break;
                    }
                }

                try (Socket sock = server.accept()) {
                    sock.setReuseAddress(true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String data = reader.readLine().trim();
                    Timber.v("Received data: \n[%s]\n", data);

                    Message message = Message.build()
                            .copyFrom(Message.decode(gson, data))
                            .setRecipient(sock.getInetAddress())
                            .createMessage();

                    publishMessage(message);
                } catch (SocketTimeoutException tex) {
                    // Timing out is expected. Timeout is set to prevent the server from running
                    // indefinitely (until a packet was received) after it was closed.
                    Timber.v("Did not receive data.");
                } catch (IOException ioe) {
                    Timber.e(ioe);
                }

            }

        } catch (IOException e) {
            Timber.e(e);
        } finally {
            Timber.d("Server was stopped");
        }
    }

    @Override
    public void send(Message<?> message) throws IOException {
        try (Socket socket = new Socket(message.getRecipient(), config.getPort())) {
            socket.setReuseAddress(true);

            byte[] data = gson.toJson(message, message.getDataType()).getBytes("US-ASCII");
            Timber.v("sending message: \n[%s]\n", message.toString());
            socket.getOutputStream().write(data);
        }
    }

    @Override
    public void close() throws IOException {
        Timber.d("Stopping server.");
        synchronized (runningLock) {
            isRunning = false;
        }
    }
}
