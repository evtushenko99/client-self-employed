package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;

import java.util.List;

public interface IExpertScheduleStatus {
    void scheduleIsLoaded(List<Appointment> expertSchedule, String expertName);

    void newAppointment(Boolean isCreate);
}
