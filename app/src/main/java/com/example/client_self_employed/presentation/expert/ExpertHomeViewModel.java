package com.example.client_self_employed.presentation.expert;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ExpertHomeViewModel extends ViewModel {
    private final ExpertInteractor mExpertScheduleInteractor;
    private final AppointmentInteractor mAppointmentInteractor;
    private final Executor mExecutor;
    private String mExpertID;
    private MutableLiveData<List<Appointment>> mObservableList = new MutableLiveData<>();
    private List<Appointment> mList = new ArrayList<>();
    private IExpertScheduleCallback mCallback = new IExpertScheduleCallback() {
        @Override
        public void scheduleIsLoaded(@Nullable List<Appointment> expertSchedule, @Nullable Expert expert) {
            if (!expertSchedule.isEmpty()) {
                mList.addAll(expertSchedule);
            }
            mObservableList.postValue(mList);
        }

        @Override
        public void newAppointment(Boolean isCreate) {

        }

        @Override
        public void messageOnWorkWithExpertSchedule(String messageOnWorkWithExpertSchedule) {

        }
    };


    public ExpertHomeViewModel(ExpertInteractor expertScheduleInteractor, AppointmentInteractor appointmentInteractor, Executor executor) {
        mExpertScheduleInteractor = expertScheduleInteractor;
        mAppointmentInteractor = appointmentInteractor;
        mExecutor = executor;
    }

    public String getExpertID() {
        return mExpertID;
    }

    public void setExpertID(@NotNull String expertID) {
        mExpertID = expertID;
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mExpertScheduleInteractor.loadExpertSchedule(expertID, mCallback);
            }
        });
    }

    public LiveData<List<Appointment>> getObservableList() {
        return mObservableList;
    }
}
