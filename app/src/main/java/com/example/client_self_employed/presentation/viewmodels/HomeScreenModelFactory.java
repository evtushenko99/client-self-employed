package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class HomeScreenModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppointmentInteractor mAppointmentInteractor;
    private ExpertInteractor mExpertInteractor;
    private FilterActiveAppointmentsInteractor mFilterActiveAppointmentsInteractor;
    private Executor mExecutor;

    @Inject
    public HomeScreenModelFactory(AppointmentInteractor iteractor,
                                  ExpertInteractor expertsInteractor,
                                  FilterActiveAppointmentsInteractor filterActiveAppointmentsInteractor,
                                  Executor executor) {
        mFilterActiveAppointmentsInteractor = filterActiveAppointmentsInteractor;
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
                    mFilterActiveAppointmentsInteractor,
                    mExecutor);
        } else {
            return super.create(modelClass);
        }

    }
}
