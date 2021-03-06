package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Client;

/**
 * Интерфейс - колбек, для передачи информации о загруженном клиенте
 * в AccountViewModel
 */
public interface IClientCallback {
    void clientIsLoaded(Client client);

    void clientsChanged(boolean isChanged);

    void messageWorkOnClient(String message);
}
