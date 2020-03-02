package com.example.client_self_employed.domain;

import androidx.annotation.Nullable;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

/**
 * Интерфейс для возвращаемых сущностей для ExpertScheduleViewModel
 * от репозитория RepositoryExpertSchedule
 */
public interface IExpertScheduleCallback {
    /**
     * Колбек - возвращает расписание определенного эксперта
     *
     * @param expertSchedule - все записи эксперта
     * @param expert         -   информация об эксперте
     */
    void scheduleIsLoaded(@Nullable List<Appointment> expertSchedule, @Nullable Expert expert);

    /**
     * Колбек для записи клиента на определенное время
     */
    void newAppointment(Boolean isCreate);

    /**
     * Колбек для вывода ошибки при загрузке расписания эксперта или
     * добавления нового клиента на определенное время
     *
     * @param messageOnWorkWithExpertSchedule
     */
    void messageOnWorkWithExpertSchedule(String messageOnWorkWithExpertSchedule);
}
