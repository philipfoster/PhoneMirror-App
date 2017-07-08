package com.github.phonemirror.phonemirrorclient.ui.addDevice;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.phonemirror.phonemirrorclient.R;
import com.github.phonemirror.phonemirrorclient.di.Injectable;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDeviceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDeviceFragment extends Fragment implements Injectable {


    @Inject
    ViewModelProvider.Factory vmFactory;

    private AddDeviceViewModel viewModel;

    public AddDeviceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

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
        return view;
    }

    @OnClick(R.id.refreshFab)
    void onRefreshClicked() {
        Timber.d("Refresh clicked");
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
