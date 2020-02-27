package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class AccountViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Executor mExecutor;
    private final ClientInteractor clientInteractor;
    private final ResourceWrapper resourceWrapper;

    @Inject
    public AccountViewModelFactory(Executor executor, ClientInteractor clientInteractor, ResourceWrapper resourceWrapper) {
        mExecutor = executor;
        this.clientInteractor = clientInteractor;
        this.resourceWrapper = resourceWrapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AccountViewModel.class.equals(modelClass)) {
            return (T) new AccountViewModel(clientInteractor, mExecutor, resourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
