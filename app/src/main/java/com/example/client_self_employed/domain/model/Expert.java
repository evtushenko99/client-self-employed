package com.example.client_self_employed.domain.model;

public class Expert {

    private long mId;
    private String mName;
    private int mAge;
    private String mEmail;
    private String mPhoneNumber;
    private String mProfession;
    private int mWorkExperience;


    public Expert() {
    }

    public Expert(long id, String name, int age, String email, String phoneNumber, String profession, int workExperience) {
        mId = id;
        mName = name;
        mAge = age;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mProfession = profession;
        mWorkExperience = workExperience;

    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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
