package com.example.client_self_employed.domain.model;

public class Appointment implements Comparable<Appointment> {
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

    public String getStringDate() {
        StringBuilder stringDate = new StringBuilder();
        stringDate.append(mDayOfMonth).append(".").append(mMonth).append(".").append(mYear);

        return stringDate.toString();
    }

    public String getStringTime() {
        StringBuilder stringTime = new StringBuilder();
        stringTime.append(mStartHourOfDay).append(":").append(mStartMinute);
        return stringTime.toString();
    }


    @Override
    public int compareTo(Appointment o) {
        if (this.getDayOfMonth() < o.getDayOfMonth()) {
            return -1;
        } else if (this.getDayOfMonth() > o.getDayOfMonth()) {
            return +1;
        } else if (this.getDayOfMonth() == o.getDayOfMonth()
                && this.getStartHourOfDay() < o.getStartHourOfDay()
                && this.getStartMinute() < o.getStartMinute()) {
            return -1;
        } else return +1;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String serviceName) {
        mServiceName = serviceName;
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

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public int getStartHourOfDay() {
        return mStartHourOfDay;
    }

    public void setStartHourOfDay(int startHourOfDay) {
        mStartHourOfDay = startHourOfDay;
    }

    public int getStartMinute() {
        return mStartMinute;
    }

    public void setStartMinute(int startMinute) {
        mStartMinute = startMinute;
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

}
