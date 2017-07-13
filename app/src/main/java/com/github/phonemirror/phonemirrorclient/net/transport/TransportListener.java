package com.github.phonemirror.phonemirrorclient.net.transport;

import com.github.phonemirror.phonemirrorclient.net.message.Message;

/**
 * A TransportListener may subscribe to a {@link TransportLayer} for network updates.
 */
@FunctionalInterface
public interface TransportListener {

    void onMessageReceived(Message object);

}
