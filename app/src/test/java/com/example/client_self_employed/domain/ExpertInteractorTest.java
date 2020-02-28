package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExpertInteractorTest {
    private ExpertInteractor mInteractor;
    private IExpertRepository mRepositoryExperts;
    private IExpertScheduleCallback mExpertScheduleCallback;


    @Before
    public void setUp() {
        mExpertScheduleCallback = mock(IExpertScheduleCallback.class);
        mRepositoryExperts = mock(IExpertRepository.class);
        mInteractor = new ExpertInteractor(mRepositoryExperts);
    }

    @Test
    public void loadAllExperts() {
        //arrange
        IExpertsCallBack callBack = mock(IExpertsCallBack.class);
        //act
        mInteractor.loadAllExperts(callBack);
        //assert
        verify(mRepositoryExperts).loadAllExperts(callBack);
    }

    @Test
    public void findExpert() {
        //arrange
        List<Expert> allExpert = createAllExpert();
        List<Expert> expected = createExpectedExpertList();
        String query = "Ма";
        //act
        List<Expert> actual = mInteractor.findExpert(allExpert, query);
        //assert
        Assert.assertThat(actual, is(expected));
    }

    @Test
    public void loadExpertSchedule() {
        //arrange
        long expertId = 0;
        //act
        mInteractor.loadExpertSchedule(expertId, mExpertScheduleCallback);
        //assert
        verify(mRepositoryExperts).loadExpertSchedule(expertId, mExpertScheduleCallback);
    }

    @Test
    public void updateExpertAppointment() {
        //arrange
        long clientId = 0;
        long appointmentId = 0;
        //act
        mInteractor.updateExpertAppointment(appointmentId, clientId, mExpertScheduleCallback);
        //assert
        verify(mRepositoryExperts).updateExpertAppointment(appointmentId, clientId, mExpertScheduleCallback);
    }

    @Test
    public void loadExperNameForActiveAppointment() {
        //arrange
        IClientAppointmentCallback callback = mock(IClientAppointmentCallback.class);
        List<Appointment> activeAppointment = new ArrayList<>();
        List<Long> expertsId = new ArrayList<>();
        //act
        mInteractor.loadExperNameForActiveAppointment(activeAppointment, expertsId, callback);
        //assert
        verify(mRepositoryExperts).loadExpertsNameForActiveAppointments(activeAppointment, expertsId, callback);
    }

    private List<Expert> createAllExpert() {
        List<Expert> allExpert = new ArrayList<>(createExpectedExpertList());
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10, null);
        allExpert.add(expert3);
        return allExpert;
    }


    private List<Expert> createExpectedExpertList() {
        List<Expert> expectedList = new ArrayList<>();
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5, null);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2, null);
        expectedList.add(expert1);
        expectedList.add(expert2);
        return expectedList;
    }
}