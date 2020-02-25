package com.example.client_self_employed.presentation.model;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointmentClickListener;

public class ClientActiveAppointmentBinding {
    private ClientAppointment mClientAppointment;
    private ObservableField<String> mExpertName = new ObservableField<>();
    private ObservableField<String> mServiceName = new ObservableField<>();// Название услуги
    private ObservableField<String> mStartTime = new ObservableField<>();// Время начала занятия
    private ObservableInt mCost = new ObservableInt();//стоимость занятия
    private ObservableField<String> mLocation = new ObservableField<>(); // место проведения
    private ObservableField<String> mDate = new ObservableField<>();
    private View.OnClickListener mOnItemClickListener;

    public ClientActiveAppointmentBinding(ClientAppointment clientAppointment) {
        mClientAppointment = clientAppointment;
        mExpertName.set(clientAppointment.getExpertName());
        mServiceName.set(clientAppointment.getServiceName());
        mStartTime.set(clientAppointment.getStartTime());
        mCost.set(clientAppointment.getCost());
        mLocation.set(clientAppointment.getLocation());
        mDate.set(clientAppointment.getDate());
    }

    public View.OnClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(ActiveAppointmentClickListener onItemClickListener, int position) {
        mOnItemClickListener = v -> onItemClickListener.onAppointmentsItemClickListener(mClientAppointment, position);
    }


    public ObservableField<String> getExpertName() {
        return mExpertName;
    }

    public ObservableField<String> getServiceName() {
        return mServiceName;
    }

    public ObservableField<String> getStartTime() {
        return mStartTime;
    }

    public ObservableInt getCost() {
        return mCost;
    }

    public ObservableField<String> getDate() {
        return mDate;
    }
}
