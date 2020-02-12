package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.RepositoryExpertSchedule;
import com.example.client_self_employed.domain.ExpertScheduleInteractor;
import com.example.client_self_employed.domain.IExpertScheduleRepository;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExpertScheduleViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public ExpertScheduleViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ExpertScheduleViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            IExpertScheduleRepository expertRepository = new RepositoryExpertSchedule();
            ExpertScheduleInteractor expertScheduleInteractor = new ExpertScheduleInteractor(expertRepository);
            return (T) new ExpertScheduleViewModel(
                    expertScheduleInteractor,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
