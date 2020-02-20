package com.example.client_self_employed.presentation.model;

import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.io.Serializable;

public class ClientAppointment implements RowType, Serializable {

    private long mAppointmentId;
    private long mExpertId;
    private String mExpertName;
    private String mServiceName;// Название услуги
    private String mStartTime;// Время начала занятия
    private int mCost;//стоимость занятия
    private String mLocation; // место проведения
    private String mDate;


    public ClientAppointment(long appointmentId, long expertId, String expertName, String serviceName, String startTime, int cost, String location, String date) {
        mAppointmentId = appointmentId;
        mExpertId = expertId;
        mExpertName = expertName;
        mServiceName = serviceName;
        mStartTime = startTime;
        mCost = cost;
        mLocation = location;
        mDate = date;
    }

    public long getExpertId() {
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


}
