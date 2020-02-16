package com.example.client_self_employed.data;

import com.example.client_self_employed.domain.IExpertScheduleCallback;

/**
 * Интерфейс для работы с расписанием определенного эксперта
 */
public interface IExpertScheduleRepository {
    /**
     * Загрузка расписания определенного эксперта
     */
    void loadExpertSchedule(long expertId, IExpertScheduleCallback expertScheduleCallback);

    /**
     * Добавление нового клиента на свободную запись
     *
     * @param appointmentId
     * @param clientId
     * @param expertScheduleCallback
     */
    void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleCallback expertScheduleCallback);
}
