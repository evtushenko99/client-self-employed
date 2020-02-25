package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
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
    private Client mClient;
    private MutableLiveData<Client> mMutableClient = new MutableLiveData<>();

    private ObservableField<String> mLastName = new ObservableField<>();
    private ObservableField<String> mName = new ObservableField<>();
    private ObservableField<String> mSecondName = new ObservableField<>();
    private ObservableField<String> mEmail = new ObservableField<>();
    private ObservableField<String> mPhoneNumber = new ObservableField<>();
    private ObservableField<String> mBirthDay = new ObservableField<>();
    private ObservableField<String> mGender = new ObservableField<>();
    private ObservableField<String> mPhotoUri = new ObservableField<>();
    private IClientCallback mCallback = new IClientCallback() {
        @Override
        public void clientIsLoaded(Client client) {
            mClient = client;
            setClientViews(client);
            mMutableClient.postValue(client);
        }

        @Override
        public void clientsChanged(boolean isChanged) {
            if (isChanged) {
                loadInformationAboutClient();
            }
        }

        @Override
        public void errorWorkOnClient(String error) {

        }
    };

    private void setClientViews(Client client) {
        mLastName.set(client.getLastName());
        mName.set(client.getFirstName());
        mSecondName.set(client.getSecondName());
        mPhotoUri.set(client.getClientPhotoUri());

        mEmail.set(client.getEmail());
        mPhoneNumber.set(client.getPhoneNumber());
        mBirthDay.set(client.getStringDate());
        mGender.set(client.getGender());
    }

    public AccountViewModel(
            @NonNull ClientInteractor clientInteractor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mClientInteractor = clientInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }


    public ObservableField<String> getLastName() {
        return mLastName;
    }

    public ObservableField<String> getName() {
        return mName;
    }

    public ObservableField<String> getSecondName() {
        return mSecondName;
    }

    public ObservableField<String> getEmail() {
        return mEmail;
    }

    public ObservableField<String> getPhoneNumber() {
        return mPhoneNumber;
    }

    public ObservableField<String> getBirthDay() {
        return mBirthDay;
    }

    public ObservableField<String> getGender() {
        return mGender;
    }

    public ObservableField<String> getPhotoUri() {
        return mPhotoUri;
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
