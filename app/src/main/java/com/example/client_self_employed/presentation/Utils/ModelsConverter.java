package com.example.client_self_employed.presentation.Utils;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.CheckedDateOfAppointmentsInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelsConverter {
    public static List<ClientAppointment> convertAppointmentToRowType(@NonNull List<Appointment> appointments, @NonNull List<Expert> experts) {
        List<ClientAppointment> data = new ArrayList<>();
        Map<Integer, String> expertsName = new HashMap<>();
        Map<Integer, Long> expertsId = new HashMap<>();

        for (int i = 0; i < appointments.size(); i++) {
            for (int j = 0; j < experts.size(); j++) {
                Expert expert = experts.get(j);
                if (appointments.get(i).getExpertId() == expert.getId()) {
                    expertsName.put(i, expert.getFullName());
                    expertsId.put(i, expert.getId());
                }
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            Calendar calendar = CheckedDateOfAppointmentsInteractor.makeCalendar(appointment);
            // if (calendar.getTimeInMillis() > System.currentTimeMillis())
            data.add(new ClientAppointment(
                    appointment.getId(),
                    expertsId.get(i),
                    expertsName.get(i),
                    appointment.getServiceName(),
                    appointment.getStringTime(),
                    appointment.getCost(),
                    appointment.getLocation(),
                    appointment.getStringDate()));
        }

        return data;
    }


}
