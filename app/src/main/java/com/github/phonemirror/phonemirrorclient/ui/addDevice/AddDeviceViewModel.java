package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.data.LoadingList;
import com.github.phonemirror.phonemirrorclient.net.NetworkScanner;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AddDeviceViewModel extends ViewModel {

    @Inject
    NetworkScanner scanner;

    private boolean isScanning = false;

    private MutableLiveData<LoadingList<Device>> devices;

    @Inject
    AddDeviceViewModel() {

    }

    MutableLiveData<LoadingList<Device>> findDevices() {
        Timber.d("Scanning network for new devices...");

        if (isScanning) {
            Timber.w("A scan is already running. Ignoring call.");
            return devices;
        }
        isScanning = true;

        Observable.fromPublisher(scanner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Device>() {

                    private LoadingList<Device> found = new LoadingList<>();

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        found.startLoading();
                        devices = new MutableLiveData<>();
                        devices.setValue(found);
                    }

                    @Override
                    public void onNext(@NonNull Device device) {
                        found.add(device);
                        devices.setValue(found);
                    }

                    @Override
                    public void onError(@NonNull Throwable t) {
                        Timber.e("onError: An error occurred while scanning", t);
                        found.finishLoading();
                        devices.setValue(found);
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete: done scanning network");
                        isScanning = false;
                        found.finishLoading();
                        devices.setValue(found);
                        devices = null;
                    }
                });

        return devices;
    }
}
