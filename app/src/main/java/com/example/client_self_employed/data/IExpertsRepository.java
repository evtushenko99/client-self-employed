package com.example.client_self_employed.data;

import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.model.Appointment;

import java.util.List;

public interface IExpertsRepository {
    void loadAllExperts(IExpertCallBack callBack);

    /**
     * Загрузка имен экпертов, которые фигурируют в активных записях клиента
     * и переда этой информации в AppointmentsViewModel
     *
     * @param activeAppointment
     * @param expertsId
     * @param dataStatus
     */
    void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback dataStatus);
}
