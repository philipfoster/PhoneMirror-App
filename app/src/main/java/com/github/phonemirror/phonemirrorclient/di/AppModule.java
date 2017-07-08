package com.github.phonemirror.phonemirrorclient.di;
/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Application;

import com.github.phonemirror.phonemirrorclient.repo.SerialRepository;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Inject
    Application app;

    public AppModule() {
    }


    @Provides
    public SerialRepository provideSerialRepo() {
        if (app == null) {
            throw new NullPointerException("app is null");
        }
        return new SerialRepository(app);
    }

}