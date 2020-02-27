package com.example.client_self_employed.dagger;

import android.content.Context;

import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class ResourceModule {
    @Provides
    public ResourceWrapper getResourceWrapper(Context context) {
        return new ResourceWrapper(context.getResources());
    }
}
