package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IAppointmentRepository;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AppointmentInteractorTest {
    private AppointmentInteractor mAppointmentInteractor;
    private IAppointmentRepository mAppointmentsRepository;
    private IAppointmentsCallback mAppointmentsCallback;
    private IClientAppointmentCallback mClientAppointmentCallback;

    @Before
    public void setUp() {
        mAppointmentsRepository = mock(IAppointmentRepository.class);
        mAppointmentInteractor = new AppointmentInteractor(mAppointmentsRepository);
        mAppointmentsCallback = mock(IAppointmentsCallback.class);
        mClientAppointmentCallback = mock(IClientAppointmentCallback.class);
    }


    @Test
    public void loadClientsAppointments() {
        long clientId = 0;
        mAppointmentInteractor.loadClientsAppointments(clientId, mAppointmentsCallback);
        verify(mAppointmentsRepository, times(1)).loadClientActiveAppointments(clientId, mAppointmentsCallback);
    }

    @Test
    public void deleteClientAppointment() {
        long clientId = 0;
        mAppointmentInteractor.deleteClientAppointment(clientId, mClientAppointmentCallback);
        verify(mAppointmentsRepository, times(1)).deleteClientsAppointment(clientId, mClientAppointmentCallback);
    }
}

