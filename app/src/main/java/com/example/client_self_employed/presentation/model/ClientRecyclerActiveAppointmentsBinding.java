package com.example.client_self_employed.presentation.model;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.presentation.adapters.AdapterActiveAppointments;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointmentClickListener;

import java.util.List;

public class ClientRecyclerActiveAppointmentsBinding {
    private ObservableField<List<ClientAppointment>> mAppointments = new ObservableField<>();
    private ActiveAppointmentClickListener mAppointmentClickListener;

    public ClientRecyclerActiveAppointmentsBinding(List<ClientAppointment> appointments) {
        mAppointments.set(appointments);
    }

    @BindingAdapter({"activeAppointments", "onAppointmentClick"})
    public static void setRecycler(RecyclerView recycler, List<ClientAppointment> appointments, ActiveAppointmentClickListener itemClickListener1) {
        if (appointments != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(recycler.getContext(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            recycler.setAdapter(new AdapterActiveAppointments(appointments, itemClickListener1));

        }
    }

    public ObservableField<List<ClientAppointment>> getAppointments() {
        return mAppointments;
    }

    public void setAppointments(ObservableField<List<ClientAppointment>> appointments) {
        mAppointments = appointments;
    }

    public ActiveAppointmentClickListener getAppointmentClickListener() {
        return mAppointmentClickListener;
    }

    public void setAppointmentClickListener(ActiveAppointmentClickListener appointmentClickListener) {
        mAppointmentClickListener = appointmentClickListener;
    }


}
