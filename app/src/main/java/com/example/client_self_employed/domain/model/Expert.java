package com.example.client_self_employed.domain.model;

import android.net.Uri;

public class Expert {

    private long mId;
    private String mLastName;
    private String mFirstName;
    private String mSecondName;//Отчество
    private int mAge;
    private String mEmail;
    private String mPhoneNumber;
    private String mProfession;
    private int mWorkExperience;
    private Uri mExpertPhotoUri;

    public Expert() {

    }

    public Expert(long id, String lastName, String firstName, String secondName, int age, String email, String phoneNumber, String profession, int workExperience) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mSecondName = secondName;
        mAge = age;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mProfession = profession;
        mWorkExperience = workExperience;
    }

    public String getFullName() {
        return mLastName + " " + mFirstName + " " + mSecondName;
    }

    public String getAbbreviatedFullName() {
        return mLastName + "." + mFirstName.substring(0, 1) + "." + mSecondName.substring(0, 1);

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

    public String getEmail() {
        return mEmail;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getProfession() {
        return mProfession;
    }

    public int getWorkExperience() {
        return mWorkExperience;
    }

    public Uri getExpertPhotoUri() {
        return mExpertPhotoUri;
    }

    public void setExpertPhotoUri(Uri expertPhotoUri) {
        mExpertPhotoUri = expertPhotoUri;
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

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public void setProfession(String profession) {
        mProfession = profession;
    }

    public void setWorkExperience(int workExperience) {
        mWorkExperience = workExperience;
    }


}