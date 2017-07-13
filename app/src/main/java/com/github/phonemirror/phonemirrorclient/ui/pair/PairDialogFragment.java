package com.github.phonemirror.phonemirrorclient.ui.pair;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.data.Device;
import com.github.phonemirror.phonemirrorclient.di.Injectable;
import com.github.phonemirror.phonemirrorclient.util.DialogLifecycleFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 *
 * This class shows the UI for pairing a new device.
 *
 * Use the {@link PairDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PairDialogFragment extends DialogLifecycleFragment implements Injectable  {

    private static final String ARG_DEVICE = "device";

    @Inject
    ViewModelProvider.Factory vmFactory;

    private Device device;
    private PairDialogViewModel viewModel;

    public PairDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment with a provided parameter.
     *
     * @param param1 the device to begin pairing with.
     * @return A new instance of fragment PairDialogFragment.
     */
    public static PairDialogFragment newInstance(Device param1) {
        PairDialogFragment fragment = new PairDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEVICE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            device = (Device) getArguments().getSerializable(ARG_DEVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pair_dialog, container, false);
        viewModel = ViewModelProviders.of(this, vmFactory).get(PairDialogViewModel.class);
        ButterKnife.bind(viewModel, view);

        setupUi();
        return view;
    }

    private void setupUi() {
        viewModel.setTitle(device.getName());
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
