package com.example.client_self_employed.domain;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.IExpertScheduleRepository;
import com.example.client_self_employed.domain.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ExpertScheduleInteractor {
    private final IExpertScheduleRepository mExpertRepository;
    private List<Appointment> mAppointments;
    private IExpertScheduleCallback mScheduleStatus;

    public ExpertScheduleInteractor(@NonNull IExpertScheduleRepository expertRepository) {
        mExpertRepository = expertRepository;
        mAppointments = new ArrayList<>();
    }

    public void loadExpertSchedule(long expertId, IExpertScheduleCallback iExpertScheduleCallback) {
        mExpertRepository.loadExpertSchedule(expertId, iExpertScheduleCallback);
    }

    public void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleCallback iExpertScheduleCallback) {
        mExpertRepository.updateExpertAppointment(appointmentId, clientId, iExpertScheduleCallback);
    }
}
