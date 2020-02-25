package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;

/**
 * Колбек для  получения информации об одной конкретной записи
 * в Fragment Detailed Appointment
 */
public interface ILoadOneAppointmentCallback {
    void oneAppointmentIsLoaded(Appointment appointment);

    void errorLoadOneAppointment(String errorLoadOneAppointment);
}
