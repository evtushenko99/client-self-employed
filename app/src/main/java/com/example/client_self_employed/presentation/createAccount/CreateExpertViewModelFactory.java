package com.example.client_self_employed.presentation.createAccount;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.CreateExpertInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class CreateExpertViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Executor mExecutor;
    private final CreateExpertInteractor mCreateExpertInteractor;
    private final ResourceWrapper mResourceWrapper;


    @Inject
    public CreateExpertViewModelFactory(Executor executor, CreateExpertInteractor clientInteractor, ResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mCreateExpertInteractor = clientInteractor;
        mResourceWrapper = resourceWrapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (CreateExpertViewModel.class.equals(modelClass)) {
            return (T) new CreateExpertViewModel(mCreateExpertInteractor, mExecutor, mResourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
