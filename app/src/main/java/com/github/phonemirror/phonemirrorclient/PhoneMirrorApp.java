package com.github.phonemirror.phonemirrorclient;

import android.app.Activity;
import android.app.Application;

import com.github.phonemirror.phonemirrorclient.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class PhoneMirrorApp extends Application implements HasActivityInjector {

    private static final String TAG = "PhoneMirrorApp";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        AppInjector.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
