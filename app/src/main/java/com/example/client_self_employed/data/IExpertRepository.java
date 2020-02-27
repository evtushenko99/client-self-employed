package com.example.client_self_employed.data;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.IExpertsCallBack;
import com.example.client_self_employed.domain.ILoadExpertPhotoCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;

import java.util.List;

public interface IExpertRepository {
    void loadAllExperts(IExpertsCallBack callBack);

    /**
     * Загрузка имен экпертов, которые фигурируют в активных записях клиента
     * и переда этой информации в AppointmentsViewModel
     */
    void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback dataStatus);

    /**
     * Загрузка расписания определенного эксперта
     */
    void loadExpertSchedule(long expertId, IExpertScheduleCallback expertScheduleCallback);

    /**
     * Добавление нового клиента на свободную запись
     */
    void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleCallback expertScheduleCallback);

    /**
     * Загрузка информации об одном эксперте
     */
    void loadOneExpert(@NonNull long expertId, ILoadOneExpertCallback callback);

    /**
     * Добавление или обновления uri - для фотографии эксперта.
     */
    void loadNewExpertPhoto(@NonNull long expertId, String newExpertPhoto, ILoadExpertPhotoCallback callback);
}
