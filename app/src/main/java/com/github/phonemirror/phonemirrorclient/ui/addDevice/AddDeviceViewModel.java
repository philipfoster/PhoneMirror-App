package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.arch.lifecycle.ViewModel;

import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.net.NetworkScanner;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AddDeviceViewModel extends ViewModel {

    @Inject
    NetworkScanner scanner;

    @Inject
    AddDeviceViewModel() {

    }



    void findDevices() {
        Timber.d("Scanning network for new devices...");

        Observable.fromPublisher(scanner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Device>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Timber.d("Subscribed");
                    }

                    @Override
                    public void onNext(Device device) {
                        Timber.d("found device %s", device.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.d("error occurred while scanning", t);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Done scanning.");
                    }
                });
    }
}
