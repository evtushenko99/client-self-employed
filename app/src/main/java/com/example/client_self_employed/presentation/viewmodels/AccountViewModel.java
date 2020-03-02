package com.example.client_self_employed.presentation.viewmodels;


import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.IClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.clicklisteners.ChangeClientBirthdayClickListener;

import java.io.File;
import java.util.concurrent.Executor;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AccountViewModel extends ViewModel {

    private final ClientInteractor mClientInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private Client mClient;
    private String mImageFileLocation = "";
    private Uri mUriForFile;
    private MutableLiveData<String> mLastName = new MutableLiveData<>();
    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<String> mSecondName = new MutableLiveData<>();
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<String> mPhoneNumber = new MutableLiveData<>();
    private MutableLiveData<String> mBirthDay = new MutableLiveData<>();
    private MutableLiveData<String> mGender = new MutableLiveData<>();
    private MutableLiveData<String> mPhotoUri = new MutableLiveData<>();
    /**
     * Слушатели для вызова dialogFragments для изменений параметров аккаунта
     */
    private View.OnClickListener mOnChangePhotoClickListener;
    private View.OnClickListener mOnChangeBirthdayClickListener;
    private View.OnClickListener mOnChangeEmailClickListener;
    private View.OnClickListener mOnChangeGenderClickListener;
    private View.OnClickListener mOnChangePhoneNumberClickListener;
    private View.OnClickListener mOnChangeFullNameClickListener;
    private View.OnClickListener mOnNotificationClickListener;

    /**
     * Слушатели для изменения существующих параметров аккаунта
     */
    /*private View.OnClickListener mOnNewClientEmailClickListener;
    private View.OnClickListener mOnNewClientPhoneNumberClickListener;
    private View.OnClickListener mOnNewClientBirthdayClickListener;*/
    private View.OnClickListener mOnNewClientFullNameClickListener;

    /**
     * Для вывода сообщения пользователю
     */
    private MutableLiveData<String> mMessage = new MutableLiveData<>();
    private IClientCallback mCallback = new IClientCallback() {
        @Override
        public void clientIsLoaded(Client client) {
            setClientViews(client);
        }

        @Override
        public void clientsChanged(boolean isChanged) {
            if (isChanged) {
                mMessage.postValue(mResourceWrapper.getString(R.string.client_information_changed));
                loadInformationAboutClient();
            }
        }

        @Override
        public void errorWorkOnClient(String error) {
            mMessage.postValue(error);
        }
    };


    public AccountViewModel(
            @NonNull ClientInteractor clientInteractor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mClientInteractor = clientInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
        loadInformationAboutClient();
    }

    void setClientViews(Client client) {
        mClient = client;
        mLastName.setValue(client.getLastName());
        mName.setValue(client.getFirstName());
        mSecondName.setValue(client.getSecondName());
        mPhotoUri.setValue(client.getClientPhotoUri());

        mEmail.setValue(client.getEmail());
        mPhoneNumber.setValue(client.getPhoneNumber());
        mBirthDay.setValue(client.getStringDate());
        mGender.setValue(client.getGender());
    }

    @BindingAdapter("clientPhotoUri")
    public static void loadClientPhoto(ImageView view, String clientPhotoUri) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide
                .with(view.getContext())
                .load(clientPhotoUri)
                .apply(new RequestOptions()
                        .centerCrop()
                        .circleCrop()
                        .placeholder(R.mipmap.no_photo_available_or_missing)
                        .error(R.mipmap.no_photo_available_or_missing))
                .transition(withCrossFade(factory))
                .into(view);

    }

    public Uri createNewImageURI() {
        if (mClient != null) {
            mExecutor.execute(() -> {
                File imageFile = mClientInteractor.createImageFile(mClient.getNewImageFileName());
                mImageFileLocation = imageFile.getAbsolutePath();
                mUriForFile = mClientInteractor.createNewImageURI(imageFile);
            });
        }
        return mUriForFile;

    }

    public void loadInformationAboutClient() {
        mExecutor.execute(() -> mClientInteractor.loadClient(2, mCallback));
    }

    public void updateFullName(String lastName, String name, String secondName) {
        mExecutor.execute(() -> mClientInteractor.updateFullName(2, lastName, name, secondName, mCallback));
    }

    public void updateClientBirthday(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        mExecutor.execute(() -> mClientInteractor.updateClientBirthday(2, dayOfBirth, monthOfBirth, yearOfBirth, mCallback));
    }

    public void updateClientEmail(String email) {
        mExecutor.execute(() -> mClientInteractor.updateClientEmail(2, email, mCallback));
    }

    public void updateClientPhoneNumber(String phoneNumber) {
        mExecutor.execute(() -> mClientInteractor.updateClientPhoneNumber(2, phoneNumber, mCallback));
    }

    public void updateClientGender(String gender) {
        mExecutor.execute(() -> mClientInteractor.updateClientGender(2, gender, mCallback));
    }

    public void loadClientImageToStorage(String newClientPhotoUri) {
        mExecutor.execute(() -> mClientInteractor.loadNewClientPhoto(2, newClientPhotoUri, mCallback));
    }

    public LiveData<String> getLastName() {
        return mLastName;
    }

    public LiveData<String> getName() {
        return mName;
    }

    public LiveData<String> getSecondName() {
        return mSecondName;
    }

    public LiveData<String> getEmail() {
        return mEmail;
    }

    public LiveData<String> getPhoneNumber() {
        return mPhoneNumber;
    }

    public LiveData<String> getBirthDay() {
        return mBirthDay;
    }

    public LiveData<String> getGender() {
        return mGender;
    }

    public LiveData<String> getPhotoUri() {
        return mPhotoUri;
    }

    public String getImageFileLocation() {
        return mImageFileLocation;
    }

    public void setOnChangeBirthdayClickListener(ChangeClientBirthdayClickListener changeBirthdayClickListener) {
        mOnChangeBirthdayClickListener = v -> {
            if (mClient != null)
                changeBirthdayClickListener.onChangeClientBirthdayClickListener(mClient.getYearOfBirth(), mClient.getMonthOfBirth(), mClient.getDayOfBirth());
        };
    }

    public View.OnClickListener getOnChangePhotoClickListener() {
        return mOnChangePhotoClickListener;
    }

    public void setOnChangePhotoClickListener(View.OnClickListener onChangePhotoClickListener) {
        mOnChangePhotoClickListener = onChangePhotoClickListener;
    }

    public View.OnClickListener getOnChangeBirthdayClickListener() {
        return mOnChangeBirthdayClickListener;
    }


    public View.OnClickListener getOnChangeEmailClickListener() {
        return mOnChangeEmailClickListener;
    }

    public void setOnChangeEmailClickListener(View.OnClickListener onChangeEmailClickListener) {
        mOnChangeEmailClickListener = onChangeEmailClickListener;
    }

    public View.OnClickListener getOnChangeGenderClickListener() {
        return mOnChangeGenderClickListener;
    }

    public void setOnChangeGenderClickListener(View.OnClickListener onChangeGenderClickListener) {
        mOnChangeGenderClickListener = onChangeGenderClickListener;
    }

    public View.OnClickListener getOnNewClientFullNameClickListener() {
        return mOnNewClientFullNameClickListener;
    }

    public void setOnNewClientFullNameClickListener(View.OnClickListener onNewClientFullNameClickListener) {
        mOnNewClientFullNameClickListener = onNewClientFullNameClickListener;
    }

    public View.OnClickListener getOnChangePhoneNumberClickListener() {
        return mOnChangePhoneNumberClickListener;
    }

    public void setOnChangePhoneNumberClickListener(View.OnClickListener onChangePhoneNumberClickListener) {
        mOnChangePhoneNumberClickListener = onChangePhoneNumberClickListener;
    }

    public View.OnClickListener getOnChangeFullNameClickListener() {
        return mOnChangeFullNameClickListener;
    }

    public void setOnChangeFullNameClickListener(View.OnClickListener onChangeFullNameClickListener) {
        mOnChangeFullNameClickListener = onChangeFullNameClickListener;
    }


    public View.OnClickListener getOnNotificationClickListener() {
        return mOnNotificationClickListener;
    }

    public void setOnNotificationClickListener(View.OnClickListener onNotificationClickListener) {
        mOnNotificationClickListener = onNotificationClickListener;
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    @VisibleForTesting
    public Client getClient() {
        return mClient;
    }

    @VisibleForTesting
    public IClientCallback getCallback() {
        return mCallback;
    }


}
