package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IAppointmentsRepository;
import com.example.client_self_employed.data.IExpertsRepository;

public class DetailedAppointmentInteractor {
    private final IAppointmentsRepository mAppointmentsRepository;
    private final IExpertsRepository mExpertsRepository;

    public DetailedAppointmentInteractor(IAppointmentsRepository appointmentsRepository, IExpertsRepository expertsRepository) {
        mAppointmentsRepository = appointmentsRepository;
        mExpertsRepository = expertsRepository;
    }

    public void loadExpert(long expertId, ILoadOneExpertCallback oneExpertCallback) {
        mExpertsRepository.loadOneExpert(expertId, oneExpertCallback);
    }

    public void loadAppointment(long appointmentId, ILoadOneAppointmentCallback oneAppointmentCallback) {
        mAppointmentsRepository.loadOneAppointment(appointmentId, oneAppointmentCallback);
    }
}
