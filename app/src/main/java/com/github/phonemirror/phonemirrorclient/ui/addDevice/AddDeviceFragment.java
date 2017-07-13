package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.data.LoadingList;
import com.github.phonemirror.phonemirrorclient.di.Injectable;
import com.github.phonemirror.phonemirrorclient.ui.NavigationController;

import javax.inject.Inject;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * This class provides the flow for discovering and pairing with new devices.
 */
public class AddDeviceFragment extends LifecycleFragment implements Injectable, OnDeviceInteractionListener {


    @Inject
    ViewModelProvider.Factory vmFactory;

    @Inject
    NavigationController navController;

    @BindView(R.id.refreshFab)
    FloatingActionButton refreshFab;

    @BindView(R.id.devicesList)
    RecyclerView recyclerView;

    @BindAnim(R.anim.spin)
    Animation rotateAnim;

    private AddDeviceViewModel viewModel;
    private AddDeviceAdapter adapter;

    /**
     * This method should not be used directly. You should use {@link #newInstance()} instead.
     */
    public AddDeviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.

     * @return A new instance of fragment AddDeviceFragment.
     */
    public static AddDeviceFragment newInstance() {
        AddDeviceFragment fragment = new AddDeviceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, vmFactory).get(AddDeviceViewModel.class);

        refreshList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_device, container, false);
        ButterKnife.bind(this, view);

        if (adapter == null) {
            adapter = new AddDeviceAdapter(this);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @OnClick(R.id.refreshFab)
    void refreshList() {
        Timber.d("Refresh clicked");

        LiveData<LoadingList<Device>> observable = viewModel.findDevices();
        if (observable != null) {
            observable.observe(this, devices -> {
                Timber.d("loading = " + devices);
                adapter.updateData(devices);

                if (devices != null) {
                    if (devices.isLoading()) {
                        if (refreshFab.getAnimation() == null) {
                            refreshFab.startAnimation(rotateAnim);
                        }
                    } else {
                        observable.removeObservers(this);
                        refreshFab.clearAnimation();
                    }
                }

            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClicked(Device item) {
        Timber.d("item clicked: %s", item.toString());
        navController.showPairFragmentDialog(item);
    }


}
