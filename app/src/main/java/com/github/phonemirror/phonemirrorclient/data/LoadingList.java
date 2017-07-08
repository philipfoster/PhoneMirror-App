package com.github.phonemirror.phonemirrorclient.data;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a wrapper around a {@link java.util.List} which contains an extra parameter {@link #isLoading()}
 * which can be used to inform a listener when loading has begun and stopped.
 */
public class LoadingList<T> extends ArrayList<T> {

    private boolean isLoading = false;

    public void startLoading() {
        isLoading = true;
    }

    public void finishLoading() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public String toString() {
        return "LoadingList{" +
                "isLoading=" + isLoading +
                ",data=" + Arrays.toString(toArray()) +
                '}';
    }
}
