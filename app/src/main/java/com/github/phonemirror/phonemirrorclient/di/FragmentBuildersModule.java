package com.github.phonemirror.phonemirrorclient.di;

import com.github.phonemirror.phonemirrorclient.ui.addDevice.AddDeviceFragment;
import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesFragment;
import com.github.phonemirror.phonemirrorclient.ui.pair.PairDialogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AddedDevicesFragment contributeDevicesFragment();

    @ContributesAndroidInjector
    abstract AddDeviceFragment contributeAddDeviceFragment();

    @ContributesAndroidInjector
    abstract PairDialogFragment contributePairDialogFragment();

}