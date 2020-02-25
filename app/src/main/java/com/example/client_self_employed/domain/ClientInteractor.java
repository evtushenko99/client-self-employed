package com.example.client_self_employed.domain;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.IClientRepository;

public class ClientInteractor {
    private final IClientRepository mClientRepository;

    public ClientInteractor(IClientRepository clientRepository) {
        mClientRepository = clientRepository;
    }

    public void loadClient(long clientId, IClientCallback clientCallback) {
        mClientRepository.loadClient(clientId, clientCallback);
    }

    public void updateClientBirthday(long clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback) {
        mClientRepository.updateClientBirthday(clientId, dayOfBirth, monthOfBirth, yearOfBirth, callback);
    }

    public void updateFullName(long clientId, String lastName, String name, String secondName, IClientCallback callback) {
        mClientRepository.updateFullName(clientId, lastName, name, secondName, callback);
    }

    public void updateClientEmail(@NonNull long clientId, String newEmail, IClientCallback callback) {
        mClientRepository.updateClientEmail(clientId, newEmail, callback);
    }

    public void updateClientGender(@NonNull long clientId, String newGender, IClientCallback callback) {
        mClientRepository.updateClientGender(clientId, newGender, callback);
    }

    public void updateClientPhoneNumber(@NonNull long clientId, String newPhoneNumber, IClientCallback callback) {
        mClientRepository.updateClientPhoneNumber(clientId, newPhoneNumber, callback);
    }

    public void loadNewClientPhoto(long clientId, String newClientPhotoUri, IClientCallback callback) {
        mClientRepository.loadNewClientPhoto(clientId, newClientPhotoUri, callback);
    }
}
