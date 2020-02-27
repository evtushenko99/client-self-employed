package com.example.client_self_employed.dagger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ExecutorModule {
    @Provides
    @Singleton
    public Executor getExecutor() {
        return Executors.newFixedThreadPool(4);
    }
}
