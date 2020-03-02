package com.example.client_self_employed.presentation.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.client_self_employed.domain.model.Appointment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Юнит тесты на {@link ExpertScheduleDetailedAppointmentViewModel}
 */
public class ExpertScheduleDetailedAppointmentViewModelTest {
    private ExpertScheduleDetailedAppointmentViewModel mViewModel;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно

    @Before
    public void setUp() {
        mViewModel = new ExpertScheduleDetailedAppointmentViewModel();
    }

    @Test
    public void setDetailedAppointment() {
        //arrange
        Appointment appointment = new Appointment(1, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 28, 10, 30,
                1,
                0, 0);
        //act
        mViewModel.setDetailedAppointment(appointment);
        //assert
        assertThat(mViewModel.getAppointmentCost().getValue(), is(appointment.getCost()));
        assertThat(mViewModel.getAppointmentDate().getValue(), is(appointment.getStringDate()));
        assertThat(mViewModel.getAppointmentDuration().getValue(), is(appointment.getSessionDuration()));
        assertThat(mViewModel.getAppointmentLocation().getValue(), is(appointment.getLocation()));
        assertThat(mViewModel.getAppointmentServiceName().getValue(), is(appointment.getServiceName()));
        assertThat(mViewModel.getAppointmentStartTime().getValue(), is(appointment.getStringTime()));
    }

}