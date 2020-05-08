package com.example.client_self_employed.presentation.clicklisteners;

import com.example.client_self_employed.domain.model.Appointment;

/**
 * Слушатель по нажатию на выбранное время в расписании эксперта
 */
public interface ExpertScheduleDetailedAppointment {
    void onExpertScheduleDetailedAppointmentClickListners(Appointment appointment, String clientId);
}
