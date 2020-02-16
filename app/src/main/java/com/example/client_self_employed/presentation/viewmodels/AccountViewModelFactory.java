package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.RepositoryClient;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccountViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mApplicationContext;

    public AccountViewModelFactory(@NonNull Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AccountViewModel.class.equals(modelClass)) {
            IClientRepository appointmentsRepository = new RepositoryClient();
            ClientInteractor clientInteractor = new ClientInteractor(appointmentsRepository);
            Executor executor = Executors.newFixedThreadPool(2);
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            return (T) new AccountViewModel(clientInteractor, executor, resourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
