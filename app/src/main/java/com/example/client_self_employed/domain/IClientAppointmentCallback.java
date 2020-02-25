package com.example.client_self_employed.domain;

import androidx.annotation.Nullable;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

/**
 * Интерфейс для возвращаемых сущностей для AppointmentsViewModel
 * от репозитория RepositoryAppointments
 */
public interface IClientAppointmentCallback {
    /**
     * Колбек для возврата активных записей клиента
     *
     * @param appointmentList - лист активных записей клиента
     * @param expertList      - лист экспертов, относящихся к автивным записям клиента
     */
    void clientsAppointmentsIsLoaded(@Nullable List<Appointment> appointmentList, @Nullable List<Expert> expertList);

    /**
     * Колбек - успешно ли произошло удаление активной записи
     */
    void clientAppointmentIsDeleted(Boolean isDeleted);

    void errorOnLoadingClientExperts(String errorOnLoadingClientExperts);

    void errorDeletingAppointment(String errorDeletingAppointment);
}
