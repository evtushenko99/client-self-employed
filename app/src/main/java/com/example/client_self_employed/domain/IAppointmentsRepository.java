package com.example.client_self_employed.domain;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;

/**
 * Интерфейс для работы загрузки активных записей клиента
 * и всех экспертов
 */
public interface IAppointmentsRepository {
    void updateAppointment(long id, int clientId);

    void deleteClientsAppointment(@NonNull long id, IAppointmentCallback status);

    void loadClientsAppointments(@NonNull long clientId, IAppointmentCallback status);

    void loadExperts(IAppointmentCallback status);

    void uploadExpert(Expert expert);

    void uploadAppointment(Appointment appointment);

    void uploadClient(Client client);

    void uploadReview(Review review);

}
