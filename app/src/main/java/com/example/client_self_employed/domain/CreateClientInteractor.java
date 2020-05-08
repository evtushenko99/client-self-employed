package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.domain.model.Client;

public class CreateClientInteractor {
    private final IClientRepository mClientRepository;

    public CreateClientInteractor(IClientRepository clientRepository) {
        mClientRepository = clientRepository;
    }

    public void createClient(Client client, String password, ICreateClientCallback createClientCallback) {
        mClientRepository.createClient(client, password, createClientCallback);
    }
}
