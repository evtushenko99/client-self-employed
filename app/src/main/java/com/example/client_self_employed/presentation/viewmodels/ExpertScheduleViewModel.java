package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ExpertScheduleViewModel extends ViewModel {
    private final Executor mExecutor;
    private long mExpertId;
    private final ExpertsIteractor mExpertScheduleInteractor;
    private final IResourceWrapper mResourceWrapper;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mExpertName = new MutableLiveData<>();


    private MutableLiveData<Boolean> mIsChanged = new MutableLiveData<>();
    private List<Appointment> mExpertAppoinments = new ArrayList<>();
    private MutableLiveData<List<Appointment>> mExpertSchedule = new MutableLiveData<>();
    private IExpertScheduleCallback mScheduleStatus = new IExpertScheduleCallback() {
        @Override
        public void scheduleIsLoaded(List<Appointment> expertSchedule, String expertName) {
            mExpertSchedule.postValue(expertSchedule);
            mExpertName.postValue(expertName);
            mIsLoading.postValue(false);
        }

        @Override
        public void newAppointment(Boolean isCreate) {
            if (isCreate && mExpertId != 0) {
                loadExpertSchedule(mExpertId);
                mIsChanged.postValue(true);
            }
        }

        @Override
        public void errorOnWorkWithExpertSchedule(String errorOnWorkWithExpertSchedule) {

        }
    };

    public ExpertScheduleViewModel(
            @NonNull ExpertsIteractor expertScheduleInteractor,
            @NonNull Executor executor,
            @NonNull IResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mExpertScheduleInteractor = expertScheduleInteractor;
        mResourceWrapper = resourceWrapper;
        mIsLoading.setValue(false);
        mIsChanged.postValue(false);
    }

    public LiveData<Boolean> getIsChanged() {
        return mIsChanged;
    }

    /***
     * Загружает расписание эксперта
     * @param id id эксперта
     */
    public void loadExpertSchedule(long id) {

        mIsLoading.setValue(true);
        mExpertId = id;
        mExecutor.execute(() -> {
            mExpertScheduleInteractor.loadExpertSchedule(mExpertId, mScheduleStatus);

        });
    }

    /**
     * Записывает клиента на определенное время
     *
     * @param appointmentId - id записи
     * @param clientId      - id клиента
     */
    public void updateExpertAppointment(long appointmentId, long clientId) {
        mIsChanged.postValue(false);
        mExecutor.execute(() -> {
            mExpertScheduleInteractor.updateExpertAppointment(appointmentId, clientId, mScheduleStatus);
        });
    }

    /**
     * Имя эксперта
     */
    @NonNull
    public LiveData<String> getExpertName() {
        return mExpertName;
    }

    /**
     * Расписание эксперта
     */
    @NonNull
    public LiveData<List<Appointment>> getExpertSchedule() {
        return mExpertSchedule;
    }

    public IResourceWrapper getResourceWrapper() {
        return mResourceWrapper;
    }


}
