package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.IAppointmentsRepository;
import com.example.client_self_employed.data.IExpertsRepository;
import com.example.client_self_employed.data.RepositoryAppointments;
import com.example.client_self_employed.data.RepositoryExperts;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailedAppointmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public DetailedAppointmentViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (DetailedAppointmentViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            IExpertsRepository expertRepository = new RepositoryExperts();
            IAppointmentsRepository appointmentsRepository = new RepositoryAppointments();
            DetailedAppointmentInteractor detailedAppointmentInteractor = new DetailedAppointmentInteractor(appointmentsRepository, expertRepository);

            return (T) new DetailedAppointmentViewModel(
                    detailedAppointmentInteractor,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
