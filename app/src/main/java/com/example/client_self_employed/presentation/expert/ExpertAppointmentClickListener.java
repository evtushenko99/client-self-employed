package com.example.client_self_employed.presentation.expert;

import com.example.client_self_employed.domain.model.Appointment;

/**
 * Слушатель по нажатию на элемент списка активных записей эксперта
 */
public interface ExpertAppointmentClickListener {
    void onExpertAppointmentClickListener(Appointment appointment, int position);
}
