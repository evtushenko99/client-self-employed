package com.example.client_self_employed.presentation.viewmodels;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;
import com.example.client_self_employed.presentation.clicklisteners.MoreInformationAboutExpertClickListener;

import java.util.List;
import java.util.concurrent.Executor;

public class ExpertScheduleViewModel extends ViewModel {
    private final ExpertInteractor mExpertScheduleInteractor;
    private final IResourceWrapper mResourceWrapper;
    private final FilterActiveAppointmentsInteractor mFilterInteractor;
    private final Executor mExecutor;

    private Expert mExpert;
    private long mExpertId;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mExpertName = new MutableLiveData<>();
    private MutableLiveData<String> mMessage = new MutableLiveData<>();


    private MutableLiveData<Boolean> mIsChanged = new MutableLiveData<>();
    private MutableLiveData<List<Appointment>> mExpertSchedule = new MutableLiveData<>();
    private View.OnClickListener mMoreInformation;
    private IExpertScheduleCallback mExpertScheduleCallback = new IExpertScheduleCallback() {
        @Override
        public void scheduleIsLoaded(List<Appointment> expertSchedule, Expert expert) {
            mExpert = expert;
            long now = System.currentTimeMillis();
            long nowForTest = 1582761600000L; // 28.02.2020
            List<Appointment> expertCheckedSchedule = mFilterInteractor.filterActiveAppointments(nowForTest, expertSchedule);
            mExpertSchedule.postValue(expertSchedule);
            mExpertName.postValue(expert.getFullName());
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
        public void messageOnWorkWithExpertSchedule(String messageOnWorkWithExpertSchedule) {
            mMessage.postValue(messageOnWorkWithExpertSchedule);
        }
    };

    public ExpertScheduleViewModel(
            @NonNull ExpertInteractor expertScheduleInteractor,
            @NonNull FilterActiveAppointmentsInteractor filterInteractor,
            @NonNull Executor executor,
            @NonNull IResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mExpertScheduleInteractor = expertScheduleInteractor;
        mFilterInteractor = filterInteractor;
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
            mExpertScheduleInteractor.loadExpertSchedule(mExpertId, mExpertScheduleCallback);

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
            mExpertScheduleInteractor.updateExpertAppointment(appointmentId, clientId, mExpertScheduleCallback);
        });
    }


    public LiveData<Boolean> getIsChanged() {
        return mIsChanged;
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

    public LiveData<String> getMessage() {
        return mMessage;
    }


    public View.OnClickListener getMoreInformation() {
        return mMoreInformation;
    }

    public void setMoreInformation(MoreInformationAboutExpertClickListener moreInformation) {
        mMoreInformation = v -> moreInformation.onMoreInformationAboutExpertClickListener(mExpert);
    }


    public void setMessage(String message) {
        mMessage = new MutableLiveData<>(message);
    }


    @VisibleForTesting
    public long getExpertId() {
        return mExpertId;
    }

    @VisibleForTesting
    public IExpertScheduleCallback getExpertScheduleCallback() {
        return mExpertScheduleCallback;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    @VisibleForTesting
    public void setExpertId(long expertId) {
        mExpertId = expertId;
    }

}
