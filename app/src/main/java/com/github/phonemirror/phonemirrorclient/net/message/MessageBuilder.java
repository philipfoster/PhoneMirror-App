package com.github.phonemirror.phonemirrorclient.net.message;


import com.github.phonemirror.phonemirrorclient.repo.SerialRepository;

import java.net.InetAddress;

public class MessageBuilder<T> {

    private String id;
    private T payload;
    private MessageType type;
    private InetAddress address;
    private int version = Message.CURRENT_VERSION;


    MessageBuilder() {}

    public MessageBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public MessageBuilder setRecipient(InetAddress address) {
        this.address = address;
        return this;
    }

    /**
     * Set the serial ID. When constructing an object for *this* installation to send, this method
     * should be used over {@link #setId(String)}.
     * @param repo the repository
     * @return a reference to this builder for method chaining.
     */
    public MessageBuilder setId(SerialRepository repo) {
        this.id = repo.getSerialId();
        return this;
    }

    public InetAddress getAddress() {
        return address;
    }

    public MessageBuilder setPayload(T payload) {
        this.payload = payload;
        return this;
    }

    public MessageBuilder setType(MessageType type) {
        this.type = type;
        return this;
    }

    public MessageBuilder setVersion(int version) {
        this.version = version;
        return this;
    }

    public Message<T> createMessage() {
        return new Message<>(id, payload, type, version, address);
    }
}