package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class HomeScreenModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppointmentInteractor mAppointmentInteractor;
    private ExpertInteractor mExpertInteractor;
    private Executor mExecutor;

    @Inject
    public HomeScreenModelFactory(AppointmentInteractor iteractor,
                                  ExpertInteractor expertsInteractor, Executor executor) {
        mAppointmentInteractor = iteractor;
        mExpertInteractor = expertsInteractor;
        mExecutor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (HomeScreenViewModel.class.equals(modelClass)) {
            return (T) new HomeScreenViewModel(
                    mAppointmentInteractor,
                    mExpertInteractor,
                    mExecutor);
        } else {
            return super.create(modelClass);
        }

    }
}
