package com.example.client_self_employed.presentation.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AccountViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;

    public AccountViewModelFactory(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    @Inject
    ClientInteractor clientInteractor;
    @Inject
    ResourceWrapper resourceWrapper;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AccountViewModel.class.equals(modelClass)) {
            Executor executor = Executors.newFixedThreadPool(2);
            ((SelfEmployedApp) mContext).getDaggerComponent().injectAccountFactory(this);
            // ResourceWrapper resourceWrapper = new ResourceWrapper(mContext.getResources());
            // IClientRepository clientRepository = new RepositoryClient(resourceWrapper);
            // ClientInteractor clientInteractor = new ClientInteractor(clientRepository);
            return (T) new AccountViewModel(clientInteractor, executor, resourceWrapper);
        } else {
            return super.create(modelClass);
        }

    }
}
