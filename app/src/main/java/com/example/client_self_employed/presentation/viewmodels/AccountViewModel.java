package com.example.client_self_employed.presentation.viewmodels;


import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
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
import com.example.client_self_employed.presentation.clicklisteners.ChangeClientInformationClickListener;

import java.util.concurrent.Executor;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AccountViewModel extends ViewModel {
    private final ClientInteractor mClientInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private Client mClient;
    private MutableLiveData<Client> mMutableClient = new MutableLiveData<>();

    private ObservableField<String> mLastName = new ObservableField<>();
    private ObservableField<String> mName = new ObservableField<>();
    private ObservableField<String> mSecondName = new ObservableField<>();
    private ObservableField<String> mEmail = new ObservableField<>();
    private ObservableField<String> mNewEmail = new ObservableField<>();
    private ObservableField<String> mPhoneNumber = new ObservableField<>();
    private ObservableField<String> mBirthDay = new ObservableField<>();
    private ObservableField<String> mGender = new ObservableField<>();
    private ObservableField<String> mPhotoUri = new ObservableField<>();
    /**
     * Слушатели для вызова dialogFragments для изменений параметров аккаунта
     */
    private View.OnClickListener mOnChangePhotoClickListener;
    private View.OnClickListener mOnChangeBirthdayClickListener;
    private View.OnClickListener mOnChangeEmailClickListener;
    private View.OnClickListener mOnChangeGenderClickListener;
    private View.OnClickListener mOnChangePhoneNumberClickListener;
    private View.OnClickListener mOnChangeFullNameClickListener;
    /**
     * Ckeifntkb для изменения существующих параметров аккаунта
     */
    private View.OnClickListener mOnNewClientEmailClickListener;
    private View.OnClickListener mOnNewClientPhoneNumberClickListener;
    private View.OnClickListener mOnNewClientBirthdayClickListener;
    private View.OnClickListener mOnNewClientFullNameClickListener;
    private IClientCallback mCallback = new IClientCallback() {
        @Override
        public void clientIsLoaded(Client client) {
            mClient = client;
            setClientViews(client);
            mMutableClient.postValue(client);
        }

        @Override
        public void clientsChanged(boolean isChanged) {
            if (isChanged) {
                loadInformationAboutClient();
            }
        }

        @Override
        public void errorWorkOnClient(String error) {

        }
    };

    private void setClientViews(Client client) {
        mLastName.set(client.getLastName());
        mName.set(client.getFirstName());
        mSecondName.set(client.getSecondName());
        mPhotoUri.set(client.getClientPhotoUri());

        mEmail.set(client.getEmail());
        mPhoneNumber.set(client.getPhoneNumber());
        mBirthDay.set(client.getStringDate());
        mGender.set(client.getGender());
    }

    public AccountViewModel(
            @NonNull ClientInteractor clientInteractor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mClientInteractor = clientInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }


    public ObservableField<String> getLastName() {
        return mLastName;
    }

    public ObservableField<String> getName() {
        return mName;
    }

    public ObservableField<String> getSecondName() {
        return mSecondName;
    }

    public ObservableField<String> getEmail() {
        return mEmail;
    }

    public ObservableField<String> getPhoneNumber() {
        return mPhoneNumber;
    }

    public ObservableField<String> getBirthDay() {
        return mBirthDay;
    }

    public ObservableField<String> getGender() {
        return mGender;
    }

    public ObservableField<String> getPhotoUri() {
        return mPhotoUri;
    }


    public ObservableField<String> getNewEmail() {
        return mNewEmail;
    }

    public void setNewEmail(ObservableField<String> newEmail) {
        mNewEmail = newEmail;
    }

    public void loadInformationAboutClient() {
        mExecutor.execute(() -> mClientInteractor.loadClient(2, mCallback));
    }

    public LiveData<Client> getMutableClient() {
        return mMutableClient;
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

    public void loadClientImageToStorage(String newImageFile) {
        mExecutor.execute(() -> mClientInteractor.loadNewClientPhoto(2, newImageFile, mCallback));
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


    public View.OnClickListener getOnChangePhotoClickListener() {
        return mOnChangePhotoClickListener;
    }

    public void setOnChangePhotoClickListener(View.OnClickListener onChangePhotoClickListener) {
        mOnChangePhotoClickListener = onChangePhotoClickListener;
    }

    public View.OnClickListener getOnChangeBirthdayClickListener() {
        return mOnChangeBirthdayClickListener;
    }

    public void setOnChangeBirthdayClickListener(View.OnClickListener onChangeBirthdayClickListener) {
        mOnChangeBirthdayClickListener = onChangeBirthdayClickListener;
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


    public View.OnClickListener getOnNewClientEmailClickListener() {
        return mOnNewClientEmailClickListener;
    }

    public void setOnNewClientEmailClickListener(ChangeClientInformationClickListener clientEmailClickListener) {
        mOnNewClientEmailClickListener = v -> clientEmailClickListener.onChangeClientClickListener(mNewEmail.get());
    }

    public View.OnClickListener getOnNewClientPhoneNumberClickListener() {
        return mOnNewClientPhoneNumberClickListener;
    }

    public void setOnNewClientPhoneNumberClickListener(View.OnClickListener onNewClientPhoneNumberClickListener) {
        mOnNewClientPhoneNumberClickListener = onNewClientPhoneNumberClickListener;
    }

    public View.OnClickListener getOnNewClientBirthdayClickListener() {
        return mOnNewClientBirthdayClickListener;
    }

    public void setOnNewClientBirthdayClickListener(View.OnClickListener onNewClientBirthdayClickListener) {
        mOnNewClientBirthdayClickListener = onNewClientBirthdayClickListener;
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


}
