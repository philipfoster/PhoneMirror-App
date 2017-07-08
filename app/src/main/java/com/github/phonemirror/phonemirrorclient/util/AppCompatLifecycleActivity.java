package com.github.phonemirror.phonemirrorclient.util;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

/**
 * This class is a combination of {@link AppCompatActivity} and {@link LifecycleActivity}
 */
public class AppCompatLifecycleActivity extends AppCompatActivity implements LifecycleOwner {

    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }
}
