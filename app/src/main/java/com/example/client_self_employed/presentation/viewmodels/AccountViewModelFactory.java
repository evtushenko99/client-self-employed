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
    private final ClientInteractor mClientInteractor;
    private final ResourceWrapper mResourceWrapper;


    @Inject
    public AccountViewModelFactory(Executor executor, ClientInteractor clientInteractor, ResourceWrapper resourceWrapper) {

        mExecutor = executor;
        mClientInteractor = clientInteractor;
        mResourceWrapper = resourceWrapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AccountViewModel.class.equals(modelClass)) {
            return (T) new AccountViewModel(mClientInteractor, mExecutor, mResourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
