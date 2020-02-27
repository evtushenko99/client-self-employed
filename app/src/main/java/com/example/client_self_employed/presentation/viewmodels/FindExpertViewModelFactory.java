package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class FindExpertViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;

    public FindExpertViewModelFactory(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Inject
    ResourceWrapper resourceWrapper;
    @Inject
    ExpertInteractor expertInteractor;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (FindExpertViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(10);
            ((SelfEmployedApp) mContext).getDaggerComponent().injectFindExpertFactory(this);
            // ResourceWrapper resourceWrapper = new ResourceWrapper(mContext.getResources());
            // IExpertRepository expertsRepository = new RepositoryExpert(resourceWrapper);
            // ExpertInteractor appointmentsIteractor = new ExpertInteractor(expertsRepository);
            return (T) new FindExpertViewModel(
                    expertInteractor,
                    executor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}


