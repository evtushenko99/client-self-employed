package com.example.client_self_employed.domain.model;

import com.example.client_self_employed.data.model.FirebaseClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Сущность клиента, содержащая освновные его характеристики
 */
public class Client {

    private long mId;
    private String mLastName;
    private String mFirstName;
    private String mSecondName;//Отчество
    private int mAge;
    private int mDayOfBirth;
    private int mMonthOfBirth;
    private int mYearOfBirth;
    private String mEmail;
    private String mPhoneNumber;
    private String mGender;
    private String mClientPhotoUri;

    public Client() {
    }

    public Client(long id, String lastName, String firstName, String secondName, int age, int dayOfBirth, int monthOfBirth, int yearOfBirth, String email, String phoneNumber, String gender, String clientPhotoUri) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mSecondName = secondName;
        mAge = age;
        mDayOfBirth = dayOfBirth;
        mMonthOfBirth = monthOfBirth;
        mYearOfBirth = yearOfBirth;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mGender = gender;
        mClientPhotoUri = clientPhotoUri;
    }

    public String getStringDate() {
        int monthInt = mMonthOfBirth + 1;
        String month = String.valueOf(monthInt);
        if (mMonthOfBirth < 10) {
            month = "0" + month;
        }
        return mDayOfBirth + "." + month + "." + mYearOfBirth;
    }

    public String getNewImageFileName() {
        String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(new Date());

        return FirebaseClient.PARENT_NAME + getId() + timeStamp;
    }

    public long getId() {
        return mId;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSecondName() {
        return mSecondName;
    }

    public int getAge() {
        return mAge;
    }

    public int getDayOfBirth() {
        return mDayOfBirth;
    }

    public int getMonthOfBirth() {
        return mMonthOfBirth;
    }

    public int getYearOfBirth() {
        return mYearOfBirth;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getGender() {
        return mGender;
    }

    public String getClientPhotoUri() {
        return mClientPhotoUri;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public void setSecondName(String secondName) {
        mSecondName = secondName;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public void setDayOfBirth(int dayOfBirth) {
        mDayOfBirth = dayOfBirth;
    }

    public void setMonthOfBirth(int monthOfBirth) {
        mMonthOfBirth = monthOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        mYearOfBirth = yearOfBirth;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public void setClientPhotoUri(String clientPhotoUri) {
        mClientPhotoUri = clientPhotoUri;
    }
}
