package com.example.client_self_employed.domain.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment implements Parcelable {

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {

        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
    private long id;
    private String serviceName;// Название услуги
    private String startTime;// Время начала занятия
    private String sessionDuration;//Продолжительность занятия
    private int cost;//стоимость занятия
    private String location; // место проведения
    private Date date;
    private long mExpertId;
    private long mClientId;
    public Appointment() {
    }

    public Appointment(long id, String serviceName, String startTime, String sessionDuration, int cost, String location, Date date, long expertId, long clientId) {
        this.id = id;

        this.serviceName = serviceName;
        this.startTime = startTime;
        this.sessionDuration = sessionDuration;
        this.cost = cost;
        this.location = location;
        this.date = date;
        mExpertId = expertId;
        mClientId = clientId;
    }

    public Appointment(Parcel in) {
        id = in.readInt();

        serviceName = in.readString();
        startTime = in.readString();
        sessionDuration = in.readString();
        cost = in.readInt();
        location = in.readString();
        date = (Date) in.readSerializable();
    }

    public long getExpertId() {
        return mExpertId;
    }

    public void setExpertId(long expertId) {
        mExpertId = expertId;
    }

    public long getClientId() {
        return mClientId;
    }

    public void setClientId(long clientId) {
        mClientId = clientId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(String sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStringDate() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");

        return format.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);

        dest.writeString(serviceName);
        dest.writeString(startTime);
        dest.writeString(sessionDuration);
        dest.writeInt(cost);
        dest.writeString(location);
        dest.writeSerializable(date);
    }
}
