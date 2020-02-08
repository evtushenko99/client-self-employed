package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

public interface IAppointmentStatus {
    void clientsAppointmentsIsLoaded(List<Appointment> appointmentList, List<Expert> expertList);
    void clientsExpertsIsLoaded(List<Expert> expertList);
    void clientAppointmentIsDeleted(Boolean isDeleted);
    void dataIsInserted();

    void dataIsUpdates();

    void dataIsDeleted();
}
