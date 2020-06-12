package com.example.client_self_employed.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.util.Objects;

/**
 * Сущность записи, содержащая освновные ее характеристики
 */
public class Appointment implements Comparable<Appointment>, Parcelable, RowType {
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
    private String mExpertId;
    private String mClientId;
    private float mRating;

    public boolean getNotification() {
        return mNotification;
    }

    public void setNotification(boolean notification) {
        mNotification = notification;
    }

    private boolean mNotification;//Есть ли уведомление

    public Appointment() {

    }

    public Appointment(long id, String serviceName, String sessionDuration, int cost, String location, int year, int month, int dayOfMonth,
                       int startHourOfDay, int startMinute, String expertId, String clientId, float rating) {
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
        mRating = rating;
        mNotification = false;
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
        mExpertId = in.readString();
        mClientId = in.readString();
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
        dest.writeString(mExpertId);
        dest.writeString(mClientId);
    }

    @Override
    public int compareTo(Appointment o) {
        if (this.getMonth() < o.getMonth()) {
            return -1;
        } else if (this.getMonth() > o.getMonth()) {
            return +1;
        } else if (this.getMonth() == o.getMonth() && this.getDayOfMonth() < o.getDayOfMonth()) {
            return -1;
        } else if (this.getMonth() == o.getMonth() && this.getDayOfMonth() > o.getDayOfMonth()) {
            return +1;
        } else if (this.getMonth() == o.getMonth() && this.getDayOfMonth() == o.getDayOfMonth()
                && this.getStartHourOfDay() < o.getStartHourOfDay()) {
            return -1;
        } else return +1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return getId() == that.getId() &&
                getCost() == that.getCost() &&
                getYear() == that.getYear() &&
                getMonth() == that.getMonth() &&
                getDayOfMonth() == that.getDayOfMonth() &&
                getStartHourOfDay() == that.getStartHourOfDay() &&
                getStartMinute() == that.getStartMinute() &&
                getExpertId() == that.getExpertId() &&
                getClientId() == that.getClientId() &&
                Float.compare(that.getRating(), getRating()) == 0 &&
                getNotification() == that.getNotification() &&
                Objects.equals(getServiceName(), that.getServiceName()) &&
                Objects.equals(getSessionDuration(), that.getSessionDuration()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getServiceName(), getSessionDuration(), getCost(), getLocation(), getYear(), getMonth(), getDayOfMonth(), getStartHourOfDay(), getStartMinute(), getExpertId(), getClientId(), getRating(), getNotification());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "mId=" + mId +
                ", mServiceName='" + mServiceName + '\'' +
                ", mSessionDuration='" + mSessionDuration + '\'' +
                ", mCost=" + mCost +
                ", mLocation='" + mLocation + '\'' +
                ", mYear=" + mYear +
                ", mMonth=" + mMonth +
                ", mDayOfMonth=" + mDayOfMonth +
                ", mStartHourOfDay=" + mStartHourOfDay +
                ", mStartMinute=" + mStartMinute +
                ", mExpertId=" + mExpertId +
                ", mClientId=" + mClientId +
                ", mRating=" + mRating +
                ", mNotification=" + mNotification +
                '}';
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

    public String getExpertId() {
        return mExpertId;
    }

    public void setExpertId(String expertId) {
        mExpertId = expertId;
    }

    public String getClientId() {
        return mClientId;
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

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }


}
