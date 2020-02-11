package com.example.client_self_employed.domain;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ScheduleInteractor {
    private final IExpertRepository mExpertRepository;
    private List<Appointment> mAppointments;
    private IExpertScheduleStatus mScheduleStatus;

    public ScheduleInteractor(@NonNull IExpertRepository expertRepository) {
        mExpertRepository = expertRepository;
        mAppointments = new ArrayList<>();
    }

    public void loadExpertSchedule(long expertId, IExpertScheduleStatus iExpertScheduleStatus) {
        mExpertRepository.loadExpertSchedule(expertId, iExpertScheduleStatus);
    }

    public void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleStatus iExpertScheduleStatus) {
        mExpertRepository.updateExpertAppointment(appointmentId, clientId, iExpertScheduleStatus);
    }
}
