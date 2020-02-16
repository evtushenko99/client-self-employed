package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.IExpertsRepository;
import com.example.client_self_employed.data.RepositoryExperts;
import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FindExpertViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public FindExpertViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (FindExpertViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            IExpertsRepository expertsRepository = new RepositoryExperts();
            ExpertsIteractor appointmentsIteractor = new ExpertsIteractor(expertsRepository);
            return (T) new FindExpertViewModel(
                    appointmentsIteractor,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}


