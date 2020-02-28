package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IFileWrapper;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientInteractorTest {
    private IClientRepository mClientRepository;
    private IFileWrapper mFileWrapper;
    private ClientInteractor mClientInteractor;
    private long mClientId = 0;
    private IClientCallback mCallback;

    @Before
    public void setUp() {
        mClientRepository = mock(IClientRepository.class);
        mFileWrapper = mock(IFileWrapper.class);
        mClientInteractor = new ClientInteractor(mClientRepository, mFileWrapper);
        mCallback = mock(IClientCallback.class);

    }

    @Test
    public void loadClient() {
        //arrange
        //act
        mClientInteractor.loadClient(mClientId, mCallback);
        //assert
        verify(mClientRepository).loadClient(mClientId, mCallback);

    }

    @Test
    public void updateClientBirthday() {
        //arrange
        int dayOfBirth = 1;
        int monthOfBirth = 10;
        int yearOfBirth = 2020;
        //act
        mClientInteractor.updateClientBirthday(mClientId, dayOfBirth, monthOfBirth, yearOfBirth, mCallback);
        //assert
        verify(mClientRepository).updateClientBirthday(mClientId, dayOfBirth, monthOfBirth, yearOfBirth, mCallback);
    }

    @Test
    public void updateFullName() {
        //arrange
        String lastName = "Evtushenko";
        String name = "Maxim";
        String secondName = "Evgenyevich";
        //act
        mClientInteractor.updateFullName(mClientId, lastName, name, secondName, mCallback);
        //assert
        verify(mClientRepository).updateFullName(mClientId, lastName, name, secondName, mCallback);

    }

    @Test
    public void updateClientEmail() {
        //arrange
        String newEmail = "evtushenko99";
        //act
        mClientInteractor.updateClientEmail(mClientId, newEmail, mCallback);
        //assert
        verify(mClientRepository).updateClientEmail(mClientId, newEmail, mCallback);

    }

    @Test
    public void updateClientGender() {
        //arrange
        String newGender = "Male";
        //act
        mClientInteractor.updateClientGender(mClientId, newGender, mCallback);
        //assert
        verify(mClientRepository).updateClientGender(mClientId, newGender, mCallback);

    }

    @Test
    public void updateClientPhoneNumber() {
        //arrange
        String newPhoneNumber = "+7-906-087-11-00";
        //act
        mClientInteractor.updateClientPhoneNumber(mClientId, newPhoneNumber, mCallback);
        //assert
        verify(mClientRepository).updateClientPhoneNumber(mClientId, newPhoneNumber, mCallback);

    }

    @Test
    public void loadNewClientPhoto() {
        //arrange
        String newClientPhotoUri = "/image_uri";
        //act
        mClientInteractor.loadNewClientPhoto(mClientId, newClientPhotoUri, mCallback);
        //assert
        verify(mClientRepository).loadNewClientPhoto(mClientId, newClientPhotoUri, mCallback);

    }

    @Test
    public void createNewImageURI() {
        //arrange
        File imageFile = mock(File.class);
        //act
        mClientInteractor.createNewImageURI(imageFile);
        //assert
        verify(mFileWrapper).createNewImageURI(imageFile);

    }

    @Test
    public void createImageFileTest() {
        //arrange
        String imageName = "expert_1";
        File imageFile = mock(File.class);
        when(mFileWrapper.createImageFile(imageName)).thenReturn(imageFile);
        //act
        File actualFile = mClientInteractor.createImageFile(imageName);
        //assert
        verify(mFileWrapper, times(1)).createImageFile(imageName);
        assertThat(actualFile, is(imageFile));
    }


}