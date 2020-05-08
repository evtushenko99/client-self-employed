package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.ICreateExpertCallback;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.IExpertsCallBack;
import com.example.client_self_employed.domain.ILoadExpertPhotoCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

public interface IExpertRepository {
    void loadAllExperts(IExpertsCallBack callBack);

    /**
     * Загрузка имен экпертов, которые фигурируют в активных записях клиента
     * и переда этой информации в AppointmentsViewModel
     */
    void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<String> expertsId, IClientAppointmentCallback dataStatus);

    /**
     * Загрузка расписания определенного эксперта
     */
    void loadExpertSchedule(@NonNull String expertId, IExpertScheduleCallback expertScheduleCallback);

    /**
     * Добавление нового клиента на свободную запись
     */
    void updateExpertAppointment(@NonNull Long appointmentId, String clientId, IExpertScheduleCallback expertScheduleCallback);

    /**
     * Загрузка информации об одном эксперте
     */
    void loadOneExpert(@NonNull String expertId, ILoadOneExpertCallback callback);

    /**
     * Создание нового эксперта
     */
    void createExpert(@NonNull Expert expert, ICreateExpertCallback createExpertCallback);

    /**
     * Добавление или обновления uri - для фотографии эксперта.
     */
    void loadNewExpertPhoto(@NonNull String expertId, String newExpertPhoto, ILoadExpertPhotoCallback callback);
}
