package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ExpertScheduleViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private final ExpertInteractor mExpertInteractor;

    @Inject
    public ExpertScheduleViewModelFactory(Executor executor, ResourceWrapper resourceWrapper, ExpertInteractor expertInteractor) {
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
        mExpertInteractor = expertInteractor;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (ExpertScheduleViewModel.class.equals(modelClass)) {
            return (T) new ExpertScheduleViewModel(
                    mExpertInteractor,
                    mExecutor,
                    mResourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
