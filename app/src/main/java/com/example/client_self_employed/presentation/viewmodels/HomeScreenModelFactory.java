package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class HomeScreenModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;

    public HomeScreenModelFactory(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Inject
    AppointmentInteractor appointmentInteractor;
    @Inject
    ExpertInteractor expertInteractor;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (HomeScreenViewModel.class.equals(modelClass)) {
            ((SelfEmployedApp) mContext).getDaggerComponent().injectHomeScreenFactory(this);
            // ResourceWrapper resourceWrapper = new ResourceWrapper(mContext.getResources());
            // IAppointmentRepository appointmentsRepository = new RepositoryAppointment(resourceWrapper);
            // IExpertRepository expertsRepository = new RepositoryExpert(resourceWrapper);
            // AppointmentInteractor appointmentInteractor = new AppointmentInteractor(appointmentsRepository);
            // ExpertInteractor expertInteractor = new ExpertInteractor(expertsRepository);
            Executor executor = Executors.newFixedThreadPool(10);
            return (T) new HomeScreenViewModel(appointmentInteractor, expertInteractor, executor);
        } else {
            return super.create(modelClass);
        }

    }
}
