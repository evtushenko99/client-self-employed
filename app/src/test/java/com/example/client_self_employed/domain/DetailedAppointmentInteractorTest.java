package com.example.client_self_employed.domain;

import androidx.work.Data;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IDataWrapper;
import com.example.client_self_employed.data.IExpertRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailedAppointmentInteractorTest {
    private IAppointmentRepository mAppointmentsRepository;
    private IExpertRepository mExpertsRepository;
    private IDataWrapper mDataWrapper;
    private DetailedAppointmentInteractor mInteractor;
    private ILoadOneAppointmentCallback mLoadOneAppointmentCallback;
    private long mAppointmentId = 0;

    @Before
    public void setUp() {
        mLoadOneAppointmentCallback = mock(ILoadOneAppointmentCallback.class);
        mAppointmentsRepository = mock(IAppointmentRepository.class);
        mExpertsRepository = mock(IExpertRepository.class);
        mDataWrapper = mock(IDataWrapper.class);
        mInteractor = new DetailedAppointmentInteractor(mAppointmentsRepository, mExpertsRepository, mDataWrapper);
    }

    @Test
    public void loadExpert() {
        //arrange
        ILoadOneExpertCallback oneExpertCallback = mock(ILoadOneExpertCallback.class);
        long expertId = 0;
        //act
        mInteractor.loadExpert(expertId, oneExpertCallback);
        //assert
        verify(mExpertsRepository).loadOneExpert(expertId, oneExpertCallback);
    }

    @Test
    public void loadAppointment() {
        //arrange

        //act
        mInteractor.loadAppointment(mAppointmentId, mLoadOneAppointmentCallback);
        //assert
        verify(mAppointmentsRepository).loadOneAppointment(mAppointmentId, mLoadOneAppointmentCallback);
    }

    @Test
    public void updateAppointmentRating() {
        //arrange
        int rating = 5;
        //act
        mInteractor.updateAppointmentRating(mAppointmentId, rating, mLoadOneAppointmentCallback);
        //assert
        verify(mAppointmentsRepository).updateAppointmentRating(mAppointmentId, rating, mLoadOneAppointmentCallback);
    }

    @Test
    public void updateAppointmentNotification() {
        //arrange
        boolean isNotification = true;
        //act
        mInteractor.updateAppointmentNotification(mAppointmentId, isNotification, mLoadOneAppointmentCallback);
        //assert
        verify(mAppointmentsRepository).updateAppointmentNotification(mAppointmentId, isNotification, mLoadOneAppointmentCallback);
    }

    @Test
    public void createWorkInputData() {
        //arrange
        long expertId = 7;
        String serviceName = "ПОдкатка";
        String startTime = "15:00";
        Data expected = Data.EMPTY;
        when(mDataWrapper.createInputData(serviceName, startTime, mAppointmentId, expertId)).thenReturn(expected);
        //act
        Data actualData = mInteractor.createWorkInputData(serviceName, startTime, mAppointmentId, expertId);
        //assert
        verify(mDataWrapper).createInputData(serviceName, startTime, mAppointmentId, expertId);
        Assert.assertThat(expected, is(actualData));
    }
}