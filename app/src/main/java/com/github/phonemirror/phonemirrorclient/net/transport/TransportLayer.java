package com.github.phonemirror.phonemirrorclient.net.transport;

import com.github.phonemirror.phonemirrorclient.net.message.Message;
import com.github.phonemirror.phonemirrorclient.net.message.MessageFilter;

import java.util.Map;
import java.util.WeakHashMap;

import timber.log.Timber;

/**
 * A TransportLayer contains logic to connect with a remote device.
 * @implNote This class only holds a weak reference to a listener, so it cannot be used to
 * keep an object from being garbage collected.
 */
public abstract class TransportLayer {

    private Map<TransportListener, MessageFilter> listeners = new WeakHashMap<>();

    /**
     * Send a message to a remote machine
     * @param message
     */
    public abstract void send(Message<?> message);

    /**
     * This method is to be used by implementing classes to
     * @param message
     */
    protected void publishMessage(Message<?> message) {
        listeners.forEach((k, v) -> {
            if (v.filter(message)) {
                k.onMessageReceived(message);
            }
        });
    }

    /**
     * Begin listening for messages recieved over the network.
     * @param listener the listener to notify
     * @param filter a filter to specify the type of message the listener is interested in
     */
    public void registerListener(TransportListener listener, MessageFilter filter) {

        if (!listeners.containsKey(listener)) {
            listeners.put(listener, filter);
        } else {
            Timber.w("This listener is already registered.");
        }
    }

    /**
     * Stop listening for new messages
     * @param listener the listener to unregister
     */
    public void unregisterListener(TransportListener listener) {
        listeners.remove(listener);
    }
}
