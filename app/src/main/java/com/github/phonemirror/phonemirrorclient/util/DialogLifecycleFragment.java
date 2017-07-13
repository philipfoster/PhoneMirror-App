package com.github.phonemirror.phonemirrorclient.util;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.DialogFragment;

/**
 * This base class merges {@link DialogFragment} and {@link android.arch.lifecycle.LifecycleFragment}
 * into a single abstract base class.
 */
public abstract class DialogLifecycleFragment extends DialogFragment implements LifecycleRegistryOwner {

    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

}
