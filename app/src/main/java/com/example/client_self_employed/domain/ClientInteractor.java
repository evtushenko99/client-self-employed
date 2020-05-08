package com.example.client_self_employed.domain;

import android.net.Uri;

import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IFileWrapper;
import com.example.client_self_employed.data.model.FirebaseClient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClientInteractor {
    private final IClientRepository mClientRepository;
    private final IFileWrapper mFileWrapper;

    public ClientInteractor(IClientRepository clientRepository, IFileWrapper fileWrapper) {
        mClientRepository = clientRepository;
        mFileWrapper = fileWrapper;
    }

    public void loadClient(String clientId, IClientCallback clientCallback) {
        mClientRepository.loadClient(clientId, clientCallback);
    }

    public void updateClientBirthday(String clientId, int dayOfBirth, int monthOfBirth, int yearOfBirth, IClientCallback callback) {
        mClientRepository.updateClientBirthday(clientId, dayOfBirth, monthOfBirth, yearOfBirth, callback);
    }

    public void updateFullName(String clientId, String lastName, String name, String secondName, IClientCallback callback) {
        mClientRepository.updateFullName(clientId, lastName, name, secondName, callback);
    }

    public void updateClientEmail(String clientId, String newEmail, IClientCallback callback) {
        mClientRepository.updateClientEmail(clientId, newEmail, callback);
    }

    public void updateClientGender(String clientId, String newGender, IClientCallback callback) {
        mClientRepository.updateClientGender(clientId, newGender, callback);
    }

    public void updateClientPhoneNumber(String clientId, String newPhoneNumber, IClientCallback callback) {
        mClientRepository.updateClientPhoneNumber(clientId, newPhoneNumber, callback);
    }

    public void loadNewClientPhoto(String clientId, String newClientPhotoUri, IClientCallback callback) {
        mClientRepository.loadNewClientPhoto(clientId, newClientPhotoUri, callback);
    }

    public Uri createNewImageURI(File imageFile) {
        return mFileWrapper.createNewImageURI(imageFile);
    }

    public File createImageFile() {
        return mFileWrapper.createImageFile(getNewImageFileName());
    }

    private String getNewImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());

        return FirebaseClient.PARENT_NAME + timeStamp;
    }
}
