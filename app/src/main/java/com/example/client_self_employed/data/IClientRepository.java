package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IClientCallback;

public interface IClientRepository {
    void loadClient(@NonNull Long clientId, IClientCallback callback);

    void updateClientBirthday(@NonNull Long clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback);

    void updateClientEmail(@NonNull Long clientId, String newEmail, IClientCallback callback);

    void updateClientGender(@NonNull Long clientId, String newGender, IClientCallback callback);

    void updateClientPhoneNumber(@NonNull Long clientId, String newPhoneNumber, IClientCallback callback);

    void loadNewClientPhoto(@NonNull Long clientId, String newClientPhoto, IClientCallback callback);

    void updateFullName(@NonNull Long clientId, String lastName, String name, String secondName, IClientCallback callback);
}
