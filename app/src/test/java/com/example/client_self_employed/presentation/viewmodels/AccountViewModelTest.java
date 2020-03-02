package com.example.client_self_employed.presentation.viewmodels;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.impl.utils.SynchronousExecutor;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит тесты на {@link AccountViewModel}
 */
public class AccountViewModelTest {
    private AccountViewModel mViewModel;
    private ClientInteractor mInteractor;
    private ResourceWrapper mResourceWrapper;
    private Client mClient;
    private IClientCallback mCallback;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно

    @Before
    public void setUp() {
        mInteractor = mock(ClientInteractor.class);
        mResourceWrapper = mock(ResourceWrapper.class);
        mViewModel = new AccountViewModel(mInteractor, new SynchronousExecutor(), mResourceWrapper);

        mClient = mock(Client.class);
        mCallback = mViewModel.getCallback();
    }

    @Test
    public void setClientViews() {
        //act
        mViewModel.setClientViews(mClient);
        //assert
        assertThat(mViewModel.getClient(), is(mClient));
        assertThat(mViewModel.getLastName().getValue(), is(mClient.getLastName()));
        assertThat(mViewModel.getName().getValue(), is(mClient.getFirstName()));
        assertThat(mViewModel.getSecondName().getValue(), is(mClient.getSecondName()));
        assertThat(mViewModel.getEmail().getValue(), is(mClient.getEmail()));
        assertThat(mViewModel.getPhoneNumber().getValue(), is(mClient.getPhoneNumber()));
        assertThat(mViewModel.getBirthDay().getValue(), is(mClient.getStringDate()));
        assertThat(mViewModel.getGender().getValue(), is(mClient.getGender()));
    }

    @Test
    public void createNewImageURI() {
        //arrange
        File imageFile = mock(File.class);
        Uri expected = mock(Uri.class);
        when(mInteractor.createImageFile(mClient.getNewImageFileName())).thenReturn(imageFile);
        when(mInteractor.createNewImageURI(imageFile)).thenReturn(expected);
        mViewModel.setClientViews(mClient);
        //act
        Uri actual = mViewModel.createNewImageURI();
        //assert
        assertThat(actual, is(expected));
        assertThat(mViewModel.getImageFileLocation(), is(imageFile.getAbsolutePath()));
        verify(mInteractor).createImageFile(mClient.getNewImageFileName());
        verify(mInteractor).createNewImageURI(imageFile);

    }

    @Test
    public void loadInformationAboutClient() {
        //act
        mViewModel.loadInformationAboutClient();
        //assert
        verify(mInteractor, times(2)).loadClient(2, mCallback);
    }

    @Test
    public void updateFullName() {
        //arrange
        String lastName = "";
        String name = "";
        String secondName = "";
        //act
        mViewModel.updateFullName(lastName, name, secondName);
        //assert
        verify(mInteractor).updateFullName(2, lastName, name, secondName, mCallback);
    }

    @Test
    public void updateClientBirthday() {
        //arrange
        int dayOfBirth = 1;
        int monthOfBirth = 2;
        int yearOfBirth = 2020;
        //act
        mViewModel.updateClientBirthday(dayOfBirth, monthOfBirth, yearOfBirth);
        //assert
        verify(mInteractor).updateClientBirthday(2, dayOfBirth, monthOfBirth, yearOfBirth, mCallback);
    }

    @Test
    public void updateClientEmail() {
        //arrange
        String email = "";
        //act
        mViewModel.updateClientEmail(email);
        //assert
        verify(mInteractor).updateClientEmail(2, email, mCallback);
    }

    @Test
    public void updateClientPhoneNumber() {
        //arrange
        String phoneNumber = "";
        //act
        mViewModel.updateClientPhoneNumber(phoneNumber);
        //assert
        verify(mInteractor).updateClientPhoneNumber(2, phoneNumber, mCallback);
    }

    @Test
    public void updateClientGender() {
        String gender = "";
        //act
        mViewModel.updateClientGender(gender);
        //assert
        verify(mInteractor).updateClientGender(2, gender, mCallback);
    }

    @Test
    public void loadClientImageToStorage() {
        String newClientPhotoUri = "";
        //act
        mViewModel.loadClientImageToStorage(newClientPhotoUri);
        //assert
        verify(mInteractor).loadNewClientPhoto(2, newClientPhotoUri, mCallback);
    }

    @Test
    public void clientCallback_loadCase() {
        //act
        mViewModel.getCallback().clientIsLoaded(mClient);
        //assert
        assertThat(mViewModel.getClient(), is(mClient));
    }

    @Test
    public void clientCallback_updateCase() {
        //act
        mViewModel.getCallback().clientsChanged(true);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(mResourceWrapper.getString(R.string.client_information_changed)));
        verify(mInteractor, times(2)).loadClient(2, mCallback);
    }

    @Test
    public void clientCallback_exceptionCase() {
        //arrange
        String message = "";
        //act
        mViewModel.getCallback().messageWorkOnClient(message);
        assertThat(mViewModel.getMessage().getValue(), is(message));
    }
}