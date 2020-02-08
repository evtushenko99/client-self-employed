package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;

import java.util.List;

public interface IAppointmentsRepository {
    void updateAppointment(long id,int clientId);
    void deleteClientsAppointment(long id,IAppointmentStatus status);


    // void loadExpertAppointments(long expertId, IAppointmentStatus dataStatus);

    void loadClientsAppointments(long clientId, IAppointmentStatus status);

    void loadExperts( IAppointmentStatus status);

    void uploadAppointment(Appointment appointment);

    void uploadExpert(Expert expert);

    void uploadClient(Client client);

    void uploadReview(Review review);

}
