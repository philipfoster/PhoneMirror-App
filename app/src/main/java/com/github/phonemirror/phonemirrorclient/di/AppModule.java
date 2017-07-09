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

import com.github.phonemirror.phonemirrorclient.net.NetworkScanner;
import com.github.phonemirror.phonemirrorclient.net.transport.TcpServer;
import com.github.phonemirror.phonemirrorclient.net.transport.TransportLayer;
import com.github.phonemirror.phonemirrorclient.repo.SerialRepository;
import com.github.phonemirror.phonemirrorclient.util.Configuration;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("WeakerAccess")
@Module(includes = ViewModelModule.class)
class AppModule {


    public AppModule() {
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public SerialRepository provideSerialRepo(Application app) {
        return new SerialRepository(app);
    }

    @Provides
    public Configuration provideConfig() {
        return new Configuration();
    }

    @Provides
    @Singleton
    public ExecutorService provideThreadPool() {
        return Executors.newCachedThreadPool();
    }

    @Provides
    @Singleton
    public TransportLayer provideTransportLayer(ExecutorService threadPool, Configuration config, Gson gson) {
        return new TcpServer(threadPool, config, gson);
    }

    @Provides
    @Singleton
    public NetworkScanner provideScanner(Configuration config, TransportLayer server,
                                         SerialRepository repo, ExecutorService threadPool, Gson gson) {
        return new NetworkScanner(config, server, repo, threadPool, gson);
    }
}