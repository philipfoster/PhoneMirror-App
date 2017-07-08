package com.github.phonemirror.phonemirrorclient.ui;


import android.support.v4.app.FragmentManager;

import com.github.phonemirror.phonemirrorclient.MainActivity;
import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesFragment;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * This controller is responsible for switching fragments.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    NavigationController(MainActivity activity) {
        containerId = R.id.fragment_container;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void showDevicesList() {
        AddedDevicesFragment fragment = AddedDevicesFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public void showAddDeviceMenu() {
        Timber.d("Switching to add device...");
    }
}
