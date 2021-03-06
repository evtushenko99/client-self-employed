package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IAppointmentsCallback;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.ILoadOneAppointmentCallback;
import com.example.client_self_employed.domain.INewAppoinmentCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;

/**
 * Интерфейс для работы загрузки активных записей клиента
 * и всех экспертов
 */
public interface IAppointmentRepository {

    void deleteClientsAppointment(@NonNull Long id, IClientAppointmentCallback status);

    void loadClientActiveAppointments(@NonNull String clientId, IAppointmentsCallback callback);

    /**
     * Загрузка информации об одной записи клиента
     */
    void updateAppointmentRating(@NonNull Long appoinmentId, float rating, ILoadOneAppointmentCallback callback);
    void loadOneAppointment(@NonNull Long appoinmentId, ILoadOneAppointmentCallback callback);

    void uploadExpert(Expert expert);

    void uploadAppointment(Appointment appointment, INewAppoinmentCallback callback);

    void uploadClient(Client client);

    void uploadReview(Review review);

    void updateAppointmentNotification(Long appointmentId, boolean isNotification, ILoadOneAppointmentCallback appointmentsCallback);
}
