package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.IExpertsRepository;
import com.example.client_self_employed.data.RepositoryExperts;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExpertSheduleDetailedAppointmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public ExpertSheduleDetailedAppointmentViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ExpertSheduleDetailedAppointmentViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            IExpertsRepository expertRepository = new RepositoryExperts();
            return (T) new ExpertSheduleDetailedAppointmentViewModel(
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
