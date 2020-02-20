package com.example.client_self_employed.presentation.model;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.ArrayList;
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
                if (appointments.get(i).getExpertId() == experts.get(j).getId()) {
                    expertsName.put(i, experts.get(j).getFullName());
                    expertsId.put(i, experts.get(j).getId());
                }
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
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
