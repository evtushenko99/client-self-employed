package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DetailedAppointmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;

    public DetailedAppointmentViewModelFactory(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Inject
    DetailedAppointmentInteractor detailedAppointmentInteractor;
    @Inject
    ResourceWrapper resourceWrapper;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (DetailedAppointmentViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ((SelfEmployedApp) mContext).getDaggerComponent().injectDetailedAppointmentFactory(this);
            // ResourceWrapper resourceWrapper = new ResourceWrapper(mContext.getResources());
            // IExpertRepository expertRepository = new RepositoryExpert(resourceWrapper);
            // IAppointmentRepository appointmentsRepository = new RepositoryAppointment(resourceWrapper);
            // DetailedAppointmentInteractor detailedAppointmentInteractor = new DetailedAppointmentInteractor(appointmentsRepository, expertRepository);

            return (T) new DetailedAppointmentViewModel(
                    detailedAppointmentInteractor,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
