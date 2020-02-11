package com.example.client_self_employed.domain;

public interface IExpertRepository {
    void loadExpertSchedule(long expertId, IExpertScheduleStatus expertScheduleStatus);

    void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleStatus expertScheduleStatus);
}
