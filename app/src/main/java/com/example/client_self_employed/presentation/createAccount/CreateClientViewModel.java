package com.example.client_self_employed.presentation.createAccount;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.CreateClientInteractor;
import com.example.client_self_employed.domain.ICreateClientCallback;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.concurrent.Executor;

public class CreateClientViewModel extends ViewModel {
    private final CreateClientInteractor mInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private final Client mNewClient;

    private MutableLiveData<String> mLastName = new MutableLiveData<>();
    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<String> mSecondName = new MutableLiveData<>();
    private MutableLiveData<String> mEmail = new MutableLiveData<>();
    private MutableLiveData<String> mPassword = new MutableLiveData<>();
    private MutableLiveData<String> mConfirmPassword = new MutableLiveData<>();
    private MutableLiveData<String> mPhoneNumber = new MutableLiveData<>();

    private MutableLiveData<String> mBirthDay = new MutableLiveData<>();

    private MutableLiveData<String> mAge = new MutableLiveData<>();
    private MutableLiveData<String> mGender = new MutableLiveData<>();
    private MutableLiveData<String> mErrorText = new MutableLiveData<>();
    private MutableLiveData<Boolean> mClientCreated = new MutableLiveData<>();
    private MutableLiveData<String> mClientUid = new MutableLiveData<>();
    /**
     * Слушатели для выбора даты дня рождения, пола и создания нового аккаунта
     */
    private View.OnClickListener mOnSelectBirthDayClickListener;
    private View.OnClickListener mOnSelectGenderClickListener;
    private View.OnClickListener mOnCreateClient;
    private ICreateClientCallback mCreateClientCallback = new ICreateClientCallback() {
        @Override
        public void clientIsCreated(boolean b, String uid) {
            if (b) {
                mClientCreated.postValue(true);
                mClientUid.postValue(uid);
            } else {
                mClientCreated.postValue(false);
            }
        }
    };

    public CreateClientViewModel(CreateClientInteractor interactor, Executor executor, ResourceWrapper resourceWrapper) {
        mInteractor = interactor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
        mNewClient = new Client();
    }

    public LiveData<String> getErrorText() {
        return mErrorText;
    }

    public LiveData<Boolean> getClientCreated() {
        return mClientCreated;
    }

    public LiveData<String> getClientUid() {
        return mClientUid;
    }

    public View.OnClickListener getOnSelectBirthDayClickListener() {
        return mOnSelectBirthDayClickListener;
    }

    public void setOnSelectBirthDayClickListener(CreateClientBirthdayClickListener onSelectBirthDayClickListener) {
        mOnSelectBirthDayClickListener = v -> {
            onSelectBirthDayClickListener.onCreateClientBirthdayClickListener(1999, 1, 2);
        };
    }

    public View.OnClickListener getOnSelectGenderClickListener() {
        return mOnSelectGenderClickListener;
    }

    public void setOnSelectGenderClickListener(View.OnClickListener onSelectGenderClickListener) {
        mOnSelectGenderClickListener = onSelectGenderClickListener;
    }

    public View.OnClickListener getOnCreateClient() {
        return mOnCreateClient;
    }

    public void setOnCreateClient(View.OnClickListener onCreateClient) {
        mOnCreateClient = onCreateClient;
    }

    public MutableLiveData<String> getLastName() {
        return mLastName;
    }

    public void setLastName(MutableLiveData<String> lastName) {
        mLastName = lastName;
    }

    public MutableLiveData<String> getName() {
        return mName;
    }

    public void setName(MutableLiveData<String> name) {
        mName = name;
    }

    public MutableLiveData<String> getSecondName() {
        return mSecondName;
    }

    public void setSecondName(MutableLiveData<String> secondName) {
        mSecondName = secondName;
    }

    public MutableLiveData<String> getEmail() {
        return mEmail;
    }

    public void setEmail(MutableLiveData<String> email) {
        mEmail = email;
    }

    public MutableLiveData<String> getAge() {
        return mAge;
    }

    public void setAge(MutableLiveData<String> age) {
        mAge = age;
    }

    public MutableLiveData<String> getPassword() {
        return mPassword;
    }

    public void setPassword(MutableLiveData<String> password) {
        mPassword = password;
    }

    public MutableLiveData<String> getConfirmPassword() {
        return mConfirmPassword;
    }

    public void setConfirmPassword(MutableLiveData<String> confirmPassword) {
        mConfirmPassword = confirmPassword;
    }

    public MutableLiveData<String> getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(MutableLiveData<String> phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public LiveData<String> getBirthDay() {
        return mBirthDay;
    }

    public LiveData<String> getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mNewClient.setGender(gender);
        mGender.postValue(mNewClient.getGender());
    }

    public void setBirthDay(int dayOfBirth, int monthOfBirth, int yearOfBirth) {
        mNewClient.setDayOfBirth(dayOfBirth);
        mNewClient.setMonthOfBirth(monthOfBirth);
        mNewClient.setYearOfBirth(yearOfBirth);
        mBirthDay.postValue(mNewClient.getStringDate());
    }

    public void onConfirm() {
        if (mPassword.getValue().equals(mConfirmPassword.getValue())) {
            mNewClient.setLastName(mLastName.getValue());
            mNewClient.setFirstName(mName.getValue());
            mNewClient.setSecondName(mSecondName.getValue());
            mNewClient.setEmail(mEmail.getValue());
            mNewClient.setPhoneNumber(mPhoneNumber.getValue());
            mNewClient.setAge(Integer.parseInt(mAge.getValue()));

            mExecutor.execute(() -> {
                mInteractor.createClient(mNewClient, mPassword.getValue(), mCreateClientCallback);
            });
        } else {
            mErrorText.setValue(mResourceWrapper.getString(R.string.passwordError));
        }
    }
}
