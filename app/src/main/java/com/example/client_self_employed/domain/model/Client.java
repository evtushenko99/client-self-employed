package com.example.client_self_employed.domain.model;

import java.util.Objects;

/**
 * Сущность клиента, содержащая освновные его характеристики
 */
public class Client {

    private String mId;
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

    public Client(String id, String lastName, String firstName, String secondName, int age, int dayOfBirth, int monthOfBirth, int yearOfBirth, String email, String phoneNumber, String gender, String clientPhotoUri) {
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

    public String getId() {
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

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return getId() == client.getId() &&
                getAge() == client.getAge() &&
                getDayOfBirth() == client.getDayOfBirth() &&
                getMonthOfBirth() == client.getMonthOfBirth() &&
                getYearOfBirth() == client.getYearOfBirth() &&
                Objects.equals(getLastName(), client.getLastName()) &&
                Objects.equals(getFirstName(), client.getFirstName()) &&
                Objects.equals(getSecondName(), client.getSecondName()) &&
                Objects.equals(getEmail(), client.getEmail()) &&
                Objects.equals(getPhoneNumber(), client.getPhoneNumber()) &&
                Objects.equals(getGender(), client.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLastName(), getFirstName(), getSecondName(), getAge(), getDayOfBirth(), getMonthOfBirth(), getYearOfBirth(), getEmail(), getPhoneNumber(), getGender(), getClientPhotoUri());
    }

    @Override
    public String toString() {
        return "Client{" +
                "mId=" + mId +
                ", mLastName='" + mLastName + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mSecondName='" + mSecondName + '\'' +
                ", mAge=" + mAge +
                ", mDayOfBirth=" + mDayOfBirth +
                ", mMonthOfBirth=" + mMonthOfBirth +
                ", mYearOfBirth=" + mYearOfBirth +
                ", mEmail='" + mEmail + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                ", mGender='" + mGender + '\'' +
                '}';
    }
}
