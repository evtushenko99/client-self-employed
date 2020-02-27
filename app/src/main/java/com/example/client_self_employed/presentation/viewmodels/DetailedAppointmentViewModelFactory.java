package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class DetailedAppointmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final DetailedAppointmentInteractor mDetailedAppointmentInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;

    @Inject
    public DetailedAppointmentViewModelFactory(DetailedAppointmentInteractor detailedAppointmentInteractor, Executor executor, ResourceWrapper resourceWrapper) {
        mDetailedAppointmentInteractor = detailedAppointmentInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (DetailedAppointmentViewModel.class.equals(modelClass)) {
            return (T) new DetailedAppointmentViewModel(
                    mDetailedAppointmentInteractor,
                    mExecutor,
                    mResourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
