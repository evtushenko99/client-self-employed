package com.example.client_self_employed.presentation.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.impl.utils.SynchronousExecutor;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит тесты на {@link ExpertScheduleViewModel}
 */
public class ExpertScheduleViewModelTest {
    private ExpertInteractor mExpertInteractor;
    private FilterActiveAppointmentsInteractor mFilterInteractor;
    private ExpertScheduleViewModel mViewModel;
    private ResourceWrapper mResourceWrapper;
    private long mExpertId = 2;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно

    @Before
    public void setUp() {
        mExpertInteractor = mock(ExpertInteractor.class);
        mFilterInteractor = mock(FilterActiveAppointmentsInteractor.class);
        mResourceWrapper = mock(ResourceWrapper.class);
        mViewModel = new ExpertScheduleViewModel(mExpertInteractor, mFilterInteractor, new SynchronousExecutor(), mResourceWrapper);
    }


    @Test
    public void loadExpertSchedule() {
        //arrange

        //act
        mViewModel.loadExpertSchedule(mExpertId);
        //assert
        assertThat(mViewModel.getIsLoading().getValue(), is(true));
        assertThat(mViewModel.getExpertId(), is(mExpertId));
        verify(mExpertInteractor).loadExpertSchedule(mExpertId, mViewModel.getExpertScheduleCallback());
    }

    @Test
    public void updateExpertAppointment() {
        //arrange
        long appointmentId = 2;
        long clientId = 1;
        //act
        mViewModel.updateExpertAppointment(appointmentId, clientId);
        //assert
        assertThat(mViewModel.getIsChanged().getValue(), is(false));
        verify(mExpertInteractor).updateExpertAppointment(appointmentId, clientId, mViewModel.getExpertScheduleCallback());
    }

    @Test
    public void expertScheduleCallback_successfulLoadedCase() {
        //arrange
        List<Appointment> expertSchedule = new ArrayList<>(createAppointments());
        List<Appointment> expertFreshSchedule = expertSchedule.subList(2, 4);
        Expert expertName = mock(Expert.class);
        long now = 1582761600000L; // 28.02.2020
        when(mFilterInteractor.filterActiveAppointments(now, expertSchedule)).thenReturn(expertFreshSchedule);
        //act
        mViewModel.getExpertScheduleCallback().scheduleIsLoaded(expertSchedule, expertName);
        //assert
        verify(mFilterInteractor).filterActiveAppointments(now, expertSchedule);

        assertThat(mViewModel.getExpertSchedule().getValue(), is(expertSchedule));
        assertThat(mViewModel.getExpertName().getValue(), is(expertName.getFullName()));
        assertThat(mViewModel.getIsLoading().getValue(), is(false));
    }

    private List<Appointment> createAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment(1, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 28, 10, 30,
                1,
                0, 0);
        Appointment appointment2 = new Appointment(2, "Подкатка", "90 минут", 15000,
                "Европейский", 2020, 2, 28, 12, 0,
                1,
                0, 0);
        Appointment appointment3 = new Appointment(3, "Подкатка", "90 минут", 500,
                "Европейский", 2020, 3, 1, 9, 0,
                1,
                0, 0);

        Appointment appointment4 = new Appointment(4, "Подкатка", "90 минут", 300,
                "Европейский", 2020, 3, 1, 15, 30,
                1,
                0, 0
        );
        Appointment appointment5 = new Appointment(5, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 3, 1, 7, 30,
                1,
                0, 0);
        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);
        appointments.add(appointment4);
        appointments.add(appointment5);
        return appointments;
    }

    @Test
    public void expertScheduleCallback_newAppointmentCase() {
        //arrange
        //act
        mViewModel.setExpertId(mExpertId);
        mViewModel.getExpertScheduleCallback().newAppointment(true);
        //assert
        verify(mExpertInteractor).loadExpertSchedule(mExpertId, mViewModel.getExpertScheduleCallback());
        assertThat(mViewModel.getIsChanged().getValue(), is(true));
        assertThat(mViewModel.getMessage().getValue(), is(mResourceWrapper.getString(R.string.expert_schedule_new_appointment)));
    }

    @Test
    public void expertScheduleCallback_exceptionCase() {
        //arrange
        String exception = "exception";
        //act
        mViewModel.getExpertScheduleCallback().messageOnWorkWithExpertSchedule(exception);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(exception));
    }
}