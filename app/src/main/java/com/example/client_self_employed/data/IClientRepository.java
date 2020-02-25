package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IClientCallback;

public interface IClientRepository {
    void loadClient(@NonNull long clientId, IClientCallback callback);

    void updateClientBirthday(@NonNull long clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback);

    void updateClientEmail(@NonNull long clientId, String newEmail, IClientCallback callback);

    void updateClientGender(@NonNull long clientId, String newGender, IClientCallback callback);

    void updateClientPhoneNumber(@NonNull long clientId, String newPhoneNumber, IClientCallback callback);

    void loadNewClientPhoto(@NonNull long clientId, String newClientPhoto, IClientCallback callback);

    void updateFullName(long clientId, String lastName, String name, String secondName, IClientCallback callback);
}
