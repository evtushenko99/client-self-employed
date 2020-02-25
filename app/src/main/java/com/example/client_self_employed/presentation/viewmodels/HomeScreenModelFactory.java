package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.IAppointmentsRepository;
import com.example.client_self_employed.data.IExpertsRepository;
import com.example.client_self_employed.data.RepositoryAppointments;
import com.example.client_self_employed.data.RepositoryExperts;
import com.example.client_self_employed.domain.AppointmentsInteractor;
import com.example.client_self_employed.domain.ExpertsIteractor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeScreenModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public HomeScreenModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (HomeScreenViewModel.class.equals(modelClass)) {
            IAppointmentsRepository appointmentsRepository = new RepositoryAppointments();
            IExpertsRepository expertsRepository = new RepositoryExperts();
            AppointmentsInteractor iteractor = new AppointmentsInteractor(appointmentsRepository, expertsRepository);
            ExpertsIteractor expertsIteractor = new ExpertsIteractor(expertsRepository);
            Executor executor = Executors.newFixedThreadPool(10);
            return (T) new HomeScreenViewModel(iteractor, expertsIteractor, executor);
        } else {
            return super.create(modelClass);
        }

    }
}
