package com.example.client_self_employed.domain.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Сущность эксперта, содержащая освновные его характеристики
 */
public class Expert implements Serializable {

    private long mId;
    private String mLastName;
    private String mFirstName;
    private String mSecondName;//Отчество
    private int mAge;
    private String mEmail;
    private String mPhoneNumber;
    private String mProfession;
    private int mWorkExperience;
    private String mExpertPhotoUri;
    private int mReviewCount;
    private int mTotalRating;

    public Expert() {

    }

    public Expert(long id, String lastName, String firstName, String secondName, int age, String email, String phoneNumber, String profession, int workExperience, String expertPhotoUri) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mSecondName = secondName;
        mAge = age;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mProfession = profession;
        mWorkExperience = workExperience;
        mExpertPhotoUri = expertPhotoUri;
    }

    public Expert(long id, String lastName, String firstName, String secondName, int age, String email, String phoneNumber, String profession, int workExperience, String expertPhotoUri, int reviewCount, int totalRating) {
        mId = id;
        mLastName = lastName;
        mFirstName = firstName;
        mSecondName = secondName;
        mAge = age;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mProfession = profession;
        mWorkExperience = workExperience;
        mExpertPhotoUri = expertPhotoUri;
        mReviewCount = reviewCount;
        mTotalRating = totalRating;
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

    public String getExpertPhotoUri() {
        return mExpertPhotoUri;
    }

    public void setExpertPhotoUri(String expertPhotoUri) {
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

    public int getReviewCount() {
        return mReviewCount;
    }

    public void setReviewCount(int reviewCount) {
        mReviewCount = reviewCount;
    }

    public int getTotalRating() {
        return mTotalRating;
    }

    public void setTotalRating(int totalRating) {
        mTotalRating = totalRating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expert)) return false;
        Expert expert = (Expert) o;
        return getId() == expert.getId() &&
                getAge() == expert.getAge() &&
                getWorkExperience() == expert.getWorkExperience() &&
                getReviewCount() == expert.getReviewCount() &&
                getTotalRating() == expert.getTotalRating() &&
                Objects.equals(getLastName(), expert.getLastName()) &&
                Objects.equals(getFirstName(), expert.getFirstName()) &&
                Objects.equals(getSecondName(), expert.getSecondName()) &&
                Objects.equals(getEmail(), expert.getEmail()) &&
                Objects.equals(getPhoneNumber(), expert.getPhoneNumber()) &&
                Objects.equals(getProfession(), expert.getProfession()) &&
                Objects.equals(getExpertPhotoUri(), expert.getExpertPhotoUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLastName(), getFirstName(), getSecondName(), getAge(), getEmail(), getPhoneNumber(), getProfession(), getWorkExperience(), getExpertPhotoUri(), getReviewCount(), getTotalRating());
    }
}
