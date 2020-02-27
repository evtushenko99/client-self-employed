package com.example.client_self_employed.presentation.viewmodels;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.model.Appointment;

public class ExpertScheduleDetailedAppointmentViewModel extends ViewModel {
    private ObservableField<String> mAppointmentServiceName = new ObservableField<>();
    private ObservableField<String> mAppointmentStartTime = new ObservableField<>();
    private ObservableField<String> mAppointmentDuration = new ObservableField<>();
    private ObservableInt mAppointmentCost = new ObservableInt();
    private ObservableField<String> mAppointmentLocation = new ObservableField<>();
    private ObservableField<String> mAppointmentDate = new ObservableField<>();
    private View.OnClickListener mOnNewAppointmentClickListener;

    public void setDetailedAppointment(Appointment appointment) {
        mAppointmentServiceName.set(appointment.getServiceName());
        mAppointmentStartTime.set(appointment.getStringTime());
        mAppointmentDuration.set(appointment.getSessionDuration());
        mAppointmentCost.set(appointment.getCost());
        mAppointmentLocation.set(appointment.getLocation());
        mAppointmentDate.set(appointment.getStringDate());
    }


    public View.OnClickListener getOnNewAppointmentClickListener() {
        return mOnNewAppointmentClickListener;
    }

    public void setOnNewAppointmentClickListener(View.OnClickListener onNewAppointmentClickListener) {
        mOnNewAppointmentClickListener = onNewAppointmentClickListener;
    }


    public ObservableField<String> getAppointmentServiceName() {
        return mAppointmentServiceName;
    }

    public ObservableField<String> getAppointmentStartTime() {
        return mAppointmentStartTime;
    }

    public ObservableField<String> getAppointmentDuration() {
        return mAppointmentDuration;
    }

    public ObservableInt getAppointmentCost() {
        return mAppointmentCost;
    }

    public ObservableField<String> getAppointmentLocation() {
        return mAppointmentLocation;
    }

    public ObservableField<String> getAppointmentDate() {
        return mAppointmentDate;
    }


}
