package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

public class AccountViewModel extends ViewModel {
    private final ClientInteractor mClientInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private MutableLiveData<Client> mMutableClient = new MutableLiveData<>();
    private IClientCallback mCallback = new IClientCallback() {
        @Override
        public void clientIsLoaded(Client client) {

            mMutableClient.postValue(client);
        }

        @Override
        public void clientBirthdayIsChanged(boolean isBirthdayChanged) {
            if (isBirthdayChanged) {
                loadInformationAboutClient();
            }
        }

        @Override
        public void clientNewPhotoIsLoaded(boolean isClientPhotoLoaded) {
            if (isClientPhotoLoaded) {
                loadInformationAboutClient();
            }
        }
    };

    public AccountViewModel(
            @NonNull ClientInteractor clientInteractor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mClientInteractor = clientInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }

    public void loadInformationAboutClient() {
        mExecutor.execute(() -> mClientInteractor.loadClient(2, mCallback));
    }

    public LiveData<Client> getMutableClient() {
        return mMutableClient;
    }

    public void updateClientBirthday(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        mExecutor.execute(() -> mClientInteractor.updateClientBirthday(2, dayOfBirth, monthOfBirth, yearOfBirth, mCallback));
    }

    public void loadClientImageToStorage(String newImageFile) {
        mExecutor.execute(() -> mClientInteractor.loadNewClientPhoto(2, newImageFile, mCallback));
    }
}
