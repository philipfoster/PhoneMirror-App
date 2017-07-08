package com.github.phonemirror.phonemirrorclient.ui.devices;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.di.Injectable;
import com.github.phonemirror.phonemirrorclient.ui.NavigationController;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class AddedDevicesFragment extends LifecycleFragment implements Injectable {


    @Inject
    ViewModelProvider.Factory vmFactory;

    @Inject
    NavigationController navController;

    private AddedDevicesViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddedDevicesFragment() {
    }

    public static AddedDevicesFragment newInstance() {
        AddedDevicesFragment fragment = new AddedDevicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, vmFactory).get(AddedDevicesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addeddevices_list, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
