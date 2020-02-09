package com.example.client_self_employed.presentation.adapters.items;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ClientAppointmentItem implements RowType, Serializable {

    private long mId;
    private String mExpertProfession;
    private String mExpertName;
    private String mExpertNumber;// Имя эксперта
    private String mServiceName;// Название услуги
    private String mStartTime;// Время начала занятия
    private String mSessionDuration;//Продолжительность занятия
    private int mCost;//стоимость занятия
    private String mLocation; // место проведения
    private String mDate;


    public ClientAppointmentItem(long id, String expertProfession, String expertName, String expertNumber, String serviceName, String startTime, String sessionDuration, int cost, String location, String date) {
        mId = id;
        mExpertProfession = expertProfession;
        mExpertName = expertName;
        mExpertNumber = expertNumber;
        mServiceName = serviceName;
        mStartTime = startTime;
        mSessionDuration = sessionDuration;
        mCost = cost;
        mLocation = location;
        mDate = date;
    }
/*
    public ClientAppointmentItem(Parcel in) {
        mId = in.readInt();
        mExpertProfession = in.readString();
        mExpertName = in.readString();
        mServiceName = in.readString();
        mStartTime = in.readString();
        mSessionDuration = in.readString();
        mCost = in.readInt();
        mLocation = in.readString();
        mDate = (Date) in.readSerializable();
    }*/

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getExpertProfession() {
        return mExpertProfession;
    }

    public void setExpertProfession(String expertProfession) {
        mExpertProfession = expertProfession;
    }

    public String getExpertNumber() {
        return mExpertNumber;
    }

    public void setExpertNumber(String expertNumber) {
        mExpertNumber = expertNumber;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public void setExpertName(String expertName) {
        mExpertName = expertName;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String serviceName) {
        mServiceName = serviceName;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getSessionDuration() {
        return mSessionDuration;
    }

    public void setSessionDuration(String sessionDuration) {
        mSessionDuration = sessionDuration;
    }

    public int getCost() {
        return mCost;
    }

    public void setCost(int cost) {
        mCost = cost;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }


    public String getStringDate() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("d.MM.yy");

        return format.format(mDate);
    }
/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mExpertProfession);
        dest.writeString(mExpertName);
        dest.writeString(mServiceName);
        dest.writeString(mStartTime);
        dest.writeString(mSessionDuration);
        dest.writeInt(mCost);
        dest.writeString(mLocation);
        dest.writeSerializable(mDate);
    }

    public static final Parcelable.Creator<ClientAppointmentItem> CREATOR = new Parcelable.Creator<ClientAppointmentItem>() {

        @Override
        public ClientAppointmentItem createFromParcel(Parcel source) {
            return new ClientAppointmentItem(source);
        }

        @Override
        public ClientAppointmentItem[] newArray(int size) {
            return new ClientAppointmentItem[size];
        }
    };*/

}
