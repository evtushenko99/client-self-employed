package com.example.client_self_employed.domain;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IFileWrapper;

import java.io.File;

public class ClientInteractor {
    private final IClientRepository mClientRepository;
    private final IFileWrapper mFileWrapper;

    public ClientInteractor(IClientRepository clientRepository, IFileWrapper fileWrapper) {
        mClientRepository = clientRepository;
        mFileWrapper = fileWrapper;
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

    public Uri createNewImageURI(File imageFile) {
        return mFileWrapper.createNewImageURI(imageFile);
    }

    public File createImageFile(String imageName) {
        return mFileWrapper.createImageFile(imageName);
    }
}
