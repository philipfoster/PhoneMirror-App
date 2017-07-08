package com.github.phonemirror.phonemirrorclient.net.message;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.InetAddress;

/**
 * This class represents a message which will be sent to a remote device. Each message contains a
 * unique identifier for the device, a {@link MessageType}, protocol version, and payload.
 */
public class Message<T> {

    static final int CURRENT_VERSION = 1;

    private String id;
    private T payload;
    private MessageType type;
    private int version = CURRENT_VERSION;

    private transient InetAddress recipient;

    Message(String id, T payload, MessageType type, int version, InetAddress address) {
        this.id = id;
        this.payload = payload;
        this.type = type;
        this.version = version;
        recipient = address;
    }

    /**
     * Create a builder instance.
     * @param <R> The type of message payload.
     */
    public static <R> MessageBuilder<R> build() {
        return new MessageBuilder<>();
    }

    /**
     * Decode a message.
     * @param <R> The type of the payload.
     * @param gson An instance of gson to decode with. If {@code null}, a default instance will be created.
     * @param buf the data to decode.
     * @return The decoded message.
     */
    public static <R> Message<R> decode(@Nullable Gson gson, String buf) {

        if (gson == null) {
            gson = new Gson();
        }

        return gson.fromJson(buf, new TypeToken<Message<R>>() {}.getType());
    }


    public String getId() {
        return id;
    }

    public T getPayload() {
        return payload;
    }

    public MessageType getMessageType() {
        return type;
    }

    public int getVersion() {
        return version;
    }

    public Class<? super Message<T>> getDataType() {
        return new TypeToken<Message<T>>() {}.getRawType();
    }


    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", payload=" + payload +
                ", type=" + type +
                ", version=" + version +
                ", recipient=" + recipient +
                '}';
    }

    public InetAddress getRecipient() {
        return recipient;
    }
}
