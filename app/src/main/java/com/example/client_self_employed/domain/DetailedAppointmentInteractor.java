package com.example.client_self_employed.domain;

import androidx.work.Data;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IDataWrapper;
import com.example.client_self_employed.data.IExpertRepository;

public class DetailedAppointmentInteractor {
    private final IAppointmentRepository mAppointmentsRepository;
    private final IExpertRepository mExpertsRepository;
    private final IDataWrapper mDataWrapper;

    public DetailedAppointmentInteractor(IAppointmentRepository appointmentsRepository, IExpertRepository expertsRepository, IDataWrapper dataWrapper) {
        mAppointmentsRepository = appointmentsRepository;
        mExpertsRepository = expertsRepository;
        mDataWrapper = dataWrapper;
    }

    public void loadExpert(String expertId, ILoadOneExpertCallback oneExpertCallback) {
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

    public Data createWorkInputData(String serviceName, String startTime, long appointmentId, String expertId) {
        return mDataWrapper.createInputData(serviceName, startTime, appointmentId, expertId);
    }
}
