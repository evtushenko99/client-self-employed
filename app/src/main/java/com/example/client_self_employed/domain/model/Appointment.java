package com.example.client_self_employed.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Сущность записи, содержащая освновные ее характеристики
 */
public class Appointment implements Comparable<Appointment>, Parcelable {
    private long mId;
    private String mServiceName;// Название услуги
    private String mSessionDuration;//Продолжительность занятия
    private int mCost;//стоимость занятия
    private String mLocation; // место проведения
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mStartHourOfDay;// Время начала занятия
    private int mStartMinute;
    private long mExpertId;
    private long mClientId;

    public Appointment() {

    }

    public Appointment(long id, String serviceName, String sessionDuration, int cost, String location, int year, int month, int dayOfMonth,
                       int startHourOfDay, int startMinute, long expertId, long clientId) {
        mId = id;
        mServiceName = serviceName;
        mSessionDuration = sessionDuration;
        mCost = cost;
        mLocation = location;
        mYear = year;
        mMonth = month;
        mDayOfMonth = dayOfMonth;
        mStartHourOfDay = startHourOfDay;
        mStartMinute = startMinute;
        mExpertId = expertId;
        mClientId = clientId;
    }

    protected Appointment(Parcel in) {
        mId = in.readLong();
        mServiceName = in.readString();
        mSessionDuration = in.readString();
        mCost = in.readInt();
        mLocation = in.readString();
        mYear = in.readInt();
        mMonth = in.readInt();
        mDayOfMonth = in.readInt();
        mStartHourOfDay = in.readInt();
        mStartMinute = in.readInt();
        mExpertId = in.readLong();
        mClientId = in.readLong();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public String getStringDate() {
        StringBuilder stringDate = new StringBuilder();
        stringDate.append(mDayOfMonth).append(".").append(mMonth).append(".").append(mYear);

        return stringDate.toString();
    }

    public String getStringTime() {
        StringBuilder stringTime = new StringBuilder();
        String startHourOfDay = String.valueOf(mStartHourOfDay);
        String startMinute = String.valueOf(mStartMinute);
        if (mStartHourOfDay == 0) {
            startHourOfDay = "00";
        }
        if (mStartMinute == 0) {
            startMinute = "00";
        }
        stringTime.append(startHourOfDay).append(":").append(startMinute);
        return stringTime.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mServiceName);
        dest.writeString(mSessionDuration);
        dest.writeInt(mCost);
        dest.writeString(mLocation);
        dest.writeInt(mYear);
        dest.writeInt(mMonth);
        dest.writeInt(mDayOfMonth);
        dest.writeInt(mStartHourOfDay);
        dest.writeInt(mStartMinute);
        dest.writeLong(mExpertId);
        dest.writeLong(mClientId);
    }

    @Override
    public int compareTo(Appointment o) {
        if (this.getDayOfMonth() < o.getDayOfMonth()) {
            return -1;
        } else if (this.getDayOfMonth() > o.getDayOfMonth()) {
            return +1;
        } else if (this.getDayOfMonth() == o.getDayOfMonth()
                && this.getStartHourOfDay() < o.getStartHourOfDay()) {
            return -1;
        } else return +1;
    }

    public long getId() {
        return mId;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public String getSessionDuration() {
        return mSessionDuration;
    }

    public int getCost() {
        return mCost;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getYear() {
        return mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public int getStartHourOfDay() {
        return mStartHourOfDay;
    }

    public int getStartMinute() {
        return mStartMinute;
    }

    public long getExpertId() {
        return mExpertId;
    }

    public long getClientId() {
        return mClientId;
    }

    public void setClientId(long clientId) {
        mClientId = clientId;
    }


    public void setId(long id) {
        mId = id;
    }

    public void setServiceName(String serviceName) {
        mServiceName = serviceName;
    }

    public void setSessionDuration(String sessionDuration) {
        mSessionDuration = sessionDuration;
    }

    public void setCost(int cost) {
        mCost = cost;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public void setStartHourOfDay(int startHourOfDay) {
        mStartHourOfDay = startHourOfDay;
    }

    public void setStartMinute(int startMinute) {
        mStartMinute = startMinute;
    }

    public void setExpertId(long expertId) {
        mExpertId = expertId;
    }


}
