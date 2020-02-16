package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;

import java.util.List;

public interface IAppointmentsCallback {
    void onAppointmentCallback(List<Appointment> appointments, List<Long> expertsId);
}
