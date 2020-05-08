package com.example.client_self_employed.presentation.createAccount;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.CreateClientInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class CreateClientViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Executor mExecutor;
    private final CreateClientInteractor mClientInteractor;
    private final ResourceWrapper mResourceWrapper;


    @Inject
    public CreateClientViewModelFactory(Executor executor, CreateClientInteractor clientInteractor, ResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mClientInteractor = clientInteractor;
        mResourceWrapper = resourceWrapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (CreateClientViewModel.class.equals(modelClass)) {
            return (T) new CreateClientViewModel(mClientInteractor, mExecutor, mResourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
