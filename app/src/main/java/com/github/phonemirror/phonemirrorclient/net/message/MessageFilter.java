package com.github.phonemirror.phonemirrorclient.net.message;

/**
 * A MessageFilter is used with {@link com.github.phonemirror.phonemirrorclient.net.transport.TransportLayer}
 * to choose which {@link com.github.phonemirror.phonemirrorclient.net.transport.TransportListener}s
 * should be notified of a new message
 */
@FunctionalInterface
public interface MessageFilter {

    /**
     * Decide whether the message should pass through the filter
     * @param msg the message to test
     * @return {@code true} if it passes, otherwise {@code false}
     */
    boolean filter(Message<?> msg);
}
