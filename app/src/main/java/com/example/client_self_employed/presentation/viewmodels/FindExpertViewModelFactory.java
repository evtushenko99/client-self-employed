package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class FindExpertViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Executor mExecutor;
    private final ResourceWrapper resourceWrapper;
    private final ExpertInteractor expertInteractor;

    @Inject
    public FindExpertViewModelFactory(Executor executor, ResourceWrapper resourceWrapper, ExpertInteractor expertInteractor) {

        mExecutor = executor;
        this.resourceWrapper = resourceWrapper;
        this.expertInteractor = expertInteractor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (FindExpertViewModel.class.equals(modelClass)) {
            return (T) new FindExpertViewModel(
                    expertInteractor,
                    mExecutor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}


