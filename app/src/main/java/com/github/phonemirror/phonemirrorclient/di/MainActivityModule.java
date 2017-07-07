package com.github.phonemirror.phonemirrorclient.di;

import com.github.phonemirror.phonemirrorclient.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by philip on 7/7/17.
 */
@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}
