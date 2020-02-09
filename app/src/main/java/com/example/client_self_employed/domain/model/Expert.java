package com.example.client_self_employed.domain.model;

public class Expert {

    private long mId;
    private String mLastName;
    private String mFirstName;
    private String mPatronymic;//Отчество
    private int mAge;
    private String mEmail;
    private String mPhoneNumber;
    private String mProfession;
    private int mWorkExperience;


    public Expert() {
    }

    public Expert(long id, String lastName, String firstName, String patronymic, int age, String email, String phoneNumber, String profession, int workExperience) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;

        mPatronymic = patronymic;
        mAge = age;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mProfession = profession;
        mWorkExperience = workExperience;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPatronymic() {
        return mPatronymic;
    }

    public void setPatronymic(String patronymic) {
        mPatronymic = patronymic;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getProfession() {
        return mProfession;
    }

    public void setProfession(String profession) {
        mProfession = profession;
    }

    public int getWorkExperience() {
        return mWorkExperience;
    }

    public void setWorkExperience(int workExperience) {
        mWorkExperience = workExperience;
    }


}
