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
    private final ScheduleInteractor mScheduleInteractor;
    private final IResourceWrapper mResourceWrapper;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<String> mExpertName = new MutableLiveData<>();
    private List<Appointment> mExpertAppoinments = new ArrayList<>();
    private MutableLiveData<List<Appointment>> mExpertSchedule = new MutableLiveData<>();

    public ExpertScheduleViewModel(
            @NonNull ScheduleInteractor scheduleInteractor,
            @NonNull Executor executor,
            @NonNull IResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mScheduleInteractor = scheduleInteractor;
        mResourceWrapper = resourceWrapper;
        mIsLoading.setValue(false);
    }

    /***
     * Загружает расписание эксперта
     * @param id id эксперта
     */
    public void loadExpertSchedule(long id) {
        mIsLoading.setValue(true);
        mExecutor.execute(() -> {
            mScheduleInteractor.loadExpertSchedule(id, new IExpertScheduleStatus() {
                @Override
                public void scheduleIsLoaded(List<Appointment> expertSchedule, String expertName) {
                    //mExpertAppoinments = expertSchedule;
                    mExpertSchedule.postValue(expertSchedule);
                    mExpertName.postValue(expertName);
                }
            });

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
}
