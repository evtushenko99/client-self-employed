package com.example.client_self_employed.presentation.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.model.Appointment;

public class ExpertScheduleDetailedAppointmentViewModel extends ViewModel {
    private MutableLiveData<String> mAppointmentServiceName = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentStartTime = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentDuration = new MutableLiveData<>();
    private MutableLiveData<Integer> mAppointmentCost = new MutableLiveData();
    private MutableLiveData<String> mAppointmentLocation = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentDate = new MutableLiveData<>();
    private View.OnClickListener mOnNewAppointmentClickListener;

    public void setDetailedAppointment(Appointment appointment) {
        mAppointmentServiceName.setValue(appointment.getServiceName());
        mAppointmentStartTime.setValue(appointment.getStringTime());
        mAppointmentDuration.setValue(appointment.getSessionDuration());
        mAppointmentCost.setValue(appointment.getCost());
        mAppointmentLocation.setValue(appointment.getLocation());
        mAppointmentDate.setValue(appointment.getStringDate());
    }


    public View.OnClickListener getOnNewAppointmentClickListener() {
        return mOnNewAppointmentClickListener;
    }

    public void setOnNewAppointmentClickListener(View.OnClickListener onNewAppointmentClickListener) {
        mOnNewAppointmentClickListener = onNewAppointmentClickListener;
    }


    public LiveData<String> getAppointmentServiceName() {
        return mAppointmentServiceName;
    }

    public LiveData<String> getAppointmentStartTime() {
        return mAppointmentStartTime;
    }

    public LiveData<String> getAppointmentDuration() {
        return mAppointmentDuration;
    }

    public LiveData<Integer> getAppointmentCost() {
        return mAppointmentCost;
    }

    public LiveData<String> getAppointmentLocation() {
        return mAppointmentLocation;
    }

    public LiveData<String> getAppointmentDate() {
        return mAppointmentDate;
    }


}
