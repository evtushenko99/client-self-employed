package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.RepositoryAppointments;
import com.example.client_self_employed.domain.AppointmentsIteractor;
import com.example.client_self_employed.domain.IAppointmentsRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppointmentsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public AppointmentsViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AppointmentsViewModel.class.equals(modelClass)) {
            IAppointmentsRepository appointmentsRepository = new RepositoryAppointments();
            AppointmentsIteractor iteractor = new AppointmentsIteractor(appointmentsRepository);
            Executor executor = Executors.newFixedThreadPool(10);
            return (T) new AppointmentsViewModel(iteractor, executor);
        } else {
            return super.create(modelClass);
        }

    }
}
