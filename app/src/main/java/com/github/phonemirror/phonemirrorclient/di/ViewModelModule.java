package com.github.phonemirror.phonemirrorclient.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddedDevicesViewModel.class)
    abstract ViewModel bindAddedDevicesViewModel(AddedDevicesViewModel vm);

//    @Binds
//    @IntoMap
//    @ViewModelKey(UserViewModel.class)
//    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel.class)
//    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RepoViewModel.class)
//    abstract ViewModel bindRepoViewModel(RepoViewModel repoViewModel);
//
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PhoneMirrorViewModelFactory factory);
}