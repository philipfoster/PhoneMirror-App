package com.github.phonemirror.phonemirrorclient.di;

import com.github.phonemirror.phonemirrorclient.ui.devices.AddedDevicesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract AddedDevicesFragment contributeDevicesFragment();
//    @ContributesAndroidInjector
//    abstract RepoFragment contributeRepoFragment();
//
//    @ContributesAndroidInjector
//    abstract UserFragment contributeUserFragment();
//
//    @ContributesAndroidInjector
//    abstract SearchFragment contributeSearchFragment();
}