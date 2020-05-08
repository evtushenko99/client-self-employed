package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.ICreateClientCallback;
import com.example.client_self_employed.domain.model.Client;

public interface IClientRepository {
    void createClient(@NonNull Client newClient, String password, ICreateClientCallback createClientCallback);

    void loadClient(@NonNull String clientId, IClientCallback callback);

    void updateClientBirthday(@NonNull String clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback);

    void updateClientEmail(@NonNull String clientId, String newEmail, IClientCallback callback);

    void updateClientGender(@NonNull String clientId, String newGender, IClientCallback callback);

    void updateClientPhoneNumber(@NonNull String clientId, String newPhoneNumber, IClientCallback callback);

    void loadNewClientPhoto(@NonNull String clientId, String newClientPhoto, IClientCallback callback);

    void updateFullName(@NonNull String clientId, String lastName, String name, String secondName, IClientCallback callback);
}
