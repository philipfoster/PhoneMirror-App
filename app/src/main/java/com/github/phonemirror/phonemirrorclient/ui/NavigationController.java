package com.github.phonemirror.phonemirrorclient.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.phonemirror.phonemirrorclient.MainActivity;
import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.ui.addDevice.AddDeviceFragment;
import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesFragment;
import com.github.phonemirror.phonemirrorclient.ui.pair.PairDialogFragment;

import javax.inject.Inject;

/**
 * This controller is responsible for switching fragments.
 */
public class NavigationController {
    private static final String DIALOG_LAYER = "dialog";

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    NavigationController(MainActivity activity) {
        containerId = R.id.fragment_container;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public void showInitialFragment() {
        AddedDevicesFragment fragment = AddedDevicesFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public void showDevicesList() {
        AddedDevicesFragment fragment = AddedDevicesFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showAddDeviceMenu() {
        AddDeviceFragment fragment = AddDeviceFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Show the pair dialog. Unlike the other non-dialog show* methods, this method will
     * overlay the fragment as a dialog instead of replacing the current active fragment.
     * @param device the device to begin pairing with.
     */
    public void showPairFragmentDialog(Device device) {
        FragmentTransaction fragTx = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(DIALOG_LAYER);
        if (prev != null) {
            fragTx.remove(prev);
        }
        fragTx.addToBackStack(null);

        PairDialogFragment newFrag = PairDialogFragment.newInstance(device);
        newFrag.show(fragTx, DIALOG_LAYER);
    }
}
