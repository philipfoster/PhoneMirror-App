package com.github.phonemirror.phonemirrorclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.github.phonemirror.phonemirrorclient.ui.addDevice.AddDeviceViewModel;
import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesViewModel;
import com.github.phonemirror.phonemirrorclient.ui.pair.PairDialogViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddedDevicesViewModel.class)
    abstract ViewModel bindAddedDevicesViewModel(AddedDevicesViewModel vm);

    @Binds
    @IntoMap
    @ViewModelKey(AddDeviceViewModel.class)
    abstract ViewModel bindAddDeviceViewModel(AddDeviceViewModel vm);

    @Binds
    @IntoMap
    @ViewModelKey(PairDialogViewModel.class)
    abstract ViewModel bindPairDialogViewModel(PairDialogViewModel vm);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PhoneMirrorViewModelFactory factory);
}