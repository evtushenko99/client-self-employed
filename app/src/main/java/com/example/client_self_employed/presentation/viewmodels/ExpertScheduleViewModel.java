package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.IExpertScheduleStatus;
import com.example.client_self_employed.domain.ScheduleInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class ExpertScheduleViewModel extends ViewModel {
    private final Executor mExecutor;
    private long mExpertId;
    private final ScheduleInteractor mScheduleInteractor;
    private final IResourceWrapper mResourceWrapper;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mExpertName = new MutableLiveData<>();

    public LiveData<Boolean> getIsChanged() {
        return mIsChanged;
    }

    private MutableLiveData<Boolean> mIsChanged = new MutableLiveData<>();
    private List<Appointment> mExpertAppoinments = new ArrayList<>();
    private MutableLiveData<List<Appointment>> mExpertSchedule = new MutableLiveData<>();
    private IExpertScheduleStatus mScheduleStatus = new IExpertScheduleStatus() {
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
    };

    public ExpertScheduleViewModel(
            @NonNull ScheduleInteractor scheduleInteractor,
            @NonNull Executor executor,
            @NonNull IResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mScheduleInteractor = scheduleInteractor;
        mResourceWrapper = resourceWrapper;
        mIsLoading.setValue(false);
        mIsChanged.postValue(false);
    }

    /***
     * Загружает расписание эксперта
     * @param id id эксперта
     */
    public void loadExpertSchedule(long id) {

        mIsLoading.setValue(true);
        mExpertId = id;
        mExecutor.execute(() -> {
            mScheduleInteractor.loadExpertSchedule(mExpertId, mScheduleStatus);

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
            mScheduleInteractor.updateExpertAppointment(appointmentId, clientId, mScheduleStatus);
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
