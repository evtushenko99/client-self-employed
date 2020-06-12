package com.example.client_self_employed.presentation.expert;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ExpertHomeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private ExpertInteractor mExpertScheduleInteractor;
    private AppointmentInteractor mAppointmentInteractor;
    private Executor mExecutor;

    @Inject
    public ExpertHomeViewModelFactory(ExpertInteractor interactor, AppointmentInteractor appointmentInteractor, Executor executor) {
        mExpertScheduleInteractor = interactor;
        mAppointmentInteractor = appointmentInteractor;
        mExecutor = executor;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ExpertHomeViewModel.class.equals(modelClass)) {
            return (T) new ExpertHomeViewModel(
                    mExpertScheduleInteractor,
                    mAppointmentInteractor,
                    mExecutor);
        } else {
            return super.create(modelClass);
        }
    }
}
