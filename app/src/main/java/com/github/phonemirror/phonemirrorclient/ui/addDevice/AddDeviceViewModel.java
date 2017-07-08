package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import timber.log.Timber;

public class AddDeviceViewModel extends ViewModel {

    @Inject
    AddDeviceViewModel() {

    }



    void findDevices() {
        Timber.d("Scanning network for new devices...");
    }
}
