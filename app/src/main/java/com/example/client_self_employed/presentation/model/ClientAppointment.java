package com.example.client_self_employed.presentation.model;

import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.io.Serializable;
import java.util.Objects;

public class ClientAppointment implements RowType, Serializable {

    private long mAppointmentId;
    private String mExpertId;
    private String mExpertName;
    private String mServiceName;// Название услуги
    private String mStartTime;// Время начала занятия
    private int mCost;//стоимость занятия
    private String mLocation; // место проведения
    private String mDate;


    public ClientAppointment(long appointmentId, String expertId, String expertName, String serviceName, String startTime, int cost, String location, String date) {
        mAppointmentId = appointmentId;
        mExpertId = expertId;
        mExpertName = expertName;
        mServiceName = serviceName;
        mStartTime = startTime;
        mCost = cost;
        mLocation = location;
        mDate = date;
    }

    public String getExpertId() {
        return mExpertId;
    }

    public long getId() {
        return mAppointmentId;
    }

    public void setId(long id) {
        mAppointmentId = id;
    }


    public String getExpertName() {
        return mExpertName;
    }


    public String getServiceName() {
        return mServiceName;
    }


    public String getStartTime() {
        return mStartTime;
    }

    public int getCost() {
        return mCost;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        return "ClientAppointment{" +
                "mAppointmentId=" + mAppointmentId +
                ", mExpertId=" + mExpertId +
                ", mExpertName='" + mExpertName + '\'' +
                ", mServiceName='" + mServiceName + '\'' +
                ", mStartTime='" + mStartTime + '\'' +
                ", mCost=" + mCost +
                ", mLocation='" + mLocation + '\'' +
                ", mDate='" + mDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientAppointment)) return false;
        ClientAppointment that = (ClientAppointment) o;
        return mAppointmentId == that.mAppointmentId &&
                getExpertId() == that.getExpertId() &&
                getCost() == that.getCost() &&
                Objects.equals(getExpertName(), that.getExpertName()) &&
                Objects.equals(getServiceName(), that.getServiceName()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAppointmentId, getExpertId(), getExpertName(), getServiceName(), getStartTime(), getCost(), getLocation(), getDate());
    }
}
