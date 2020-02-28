package com.example.client_self_employed;

import android.app.Application;

import com.example.client_self_employed.dagger.ContextModule;
import com.example.client_self_employed.dagger.DaggerFactoryComponent;
import com.example.client_self_employed.dagger.DataModule;
import com.example.client_self_employed.dagger.FactoryComponent;
import com.example.client_self_employed.dagger.InteractorModule;
import com.example.client_self_employed.dagger.ResourceModule;

public class SelfEmployedApp extends Application {
    FactoryComponent mDaggerComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mDaggerComponent = DaggerFactoryComponent
                .builder()
                .contextModule(new ContextModule(this))
                .interactorModule(new InteractorModule())
                .dataModule(new DataModule())
                .resourceModule(new ResourceModule())
                .build();
    }

    public FactoryComponent getDaggerComponent() {
        return mDaggerComponent;
    }


}
