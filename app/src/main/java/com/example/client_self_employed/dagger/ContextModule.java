package com.example.client_self_employed.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context getContext() {
        return mContext.getApplicationContext();
    }
}
