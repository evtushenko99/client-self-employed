package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.domain.model.Appointment;

public class AppointmentInteractor {
    private final IAppointmentRepository mAppointmentsRepository;

    public AppointmentInteractor(IAppointmentRepository appointmentsRepository) {
        mAppointmentsRepository = appointmentsRepository;
    }

    public void uploadAppoinment(Appointment appointment, INewAppoinmentCallback callback) {
        mAppointmentsRepository.uploadAppointment(appointment, callback);
    }

    public void loadClientsAppointments(String clientId, IAppointmentsCallback appointmentsCallback) {
        mAppointmentsRepository.loadClientActiveAppointments(clientId, appointmentsCallback);
    }

    public void deleteClientAppointment(long appoinmtentId, IClientAppointmentCallback status) {
        mAppointmentsRepository.deleteClientsAppointment(appoinmtentId, status);
    }


}
