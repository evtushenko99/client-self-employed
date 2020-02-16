package com.example.client_self_employed.domain;

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

    public void loadNewClientPhoto(long clientId, String newClientPhoto, IClientCallback callback) {
        mClientRepository.loadNewClientPhoto(clientId, newClientPhoto, callback);
    }
}
