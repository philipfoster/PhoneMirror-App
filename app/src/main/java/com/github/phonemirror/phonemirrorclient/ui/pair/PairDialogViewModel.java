package com.github.phonemirror.phonemirrorclient.ui.pair;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * ViewModel for {@link PairDialogFragment}
 */
public class PairDialogViewModel extends ViewModel {


    @Inject
    PairDialogViewModel() {

    }

    void beginQrPair() {
        Timber.d("QR");
    }

    void beginPasswordPair() {
        Timber.d("PW");
    }

    void beginUsbPair() {
        Timber.d("USB");
    }

}
