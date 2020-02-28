package com.example.client_self_employed.presentation.clicklisteners;

import com.example.client_self_employed.presentation.model.ClientAppointment;

/**
 * Слушатель по нажатию на элемент списка активных записей
 */
public interface ActiveAppointmentClickListener {
    void onAppointmentsItemClickListener(ClientAppointment appointment, int position);
}
