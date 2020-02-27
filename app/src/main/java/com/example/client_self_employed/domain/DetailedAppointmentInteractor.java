package com.example.client_self_employed.domain;

import androidx.work.Data;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.notification.Constants;

public class DetailedAppointmentInteractor {
    private final IAppointmentRepository mAppointmentsRepository;
    private final IExpertRepository mExpertsRepository;

    public DetailedAppointmentInteractor(IAppointmentRepository appointmentsRepository, IExpertRepository expertsRepository) {
        mAppointmentsRepository = appointmentsRepository;
        mExpertsRepository = expertsRepository;
    }

    public void loadExpert(long expertId, ILoadOneExpertCallback oneExpertCallback) {
        mExpertsRepository.loadOneExpert(expertId, oneExpertCallback);
    }

    public void loadAppointment(long appointmentId, ILoadOneAppointmentCallback oneAppointmentCallback) {
        mAppointmentsRepository.loadOneAppointment(appointmentId, oneAppointmentCallback);
    }

    public void updateAppointmentRating(long appointmentId, float rating, ILoadOneAppointmentCallback appointmentsCallback) {
        mAppointmentsRepository.updateAppointmentRating(appointmentId, rating, appointmentsCallback);
    }

    public void updateAppointmentNotification(long appointmentId, boolean isNotification, ILoadOneAppointmentCallback appointmentsCallback) {
        mAppointmentsRepository.updateAppointmentNotification(appointmentId, isNotification, appointmentsCallback);
    }

    public Data createWorkInputData(String serviceName, String startTime, long appointmentId, long expertId) {
        return new Data.Builder()
                .putString(Constants.EXTRA_TITLE, serviceName)
                .putString(Constants.EXTRA_TEXT, startTime)
                .putLong(Constants.EXTRA_EXPERT_ID, expertId)
                .putLong(Constants.EXTRA_APPOINTMENT_ID, appointmentId)
                .build();
    }
}
