package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.AppointmentsInteractor;
import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class AppointmentsViewModel extends ViewModel {
    private final AppointmentsInteractor mAppointmentsInteractor;
    private final ExpertsIteractor mExpertsIteractor;
    private final Executor mExecutor;
    private final MutableLiveData<Boolean> mIsBestExpertLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsActiveAppointmentLoading = new MutableLiveData<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();
    private MutableLiveData<List<RowType>> mLiveData = new MutableLiveData<>();
    private List<RowType> mRowTypes = new ArrayList<>();

    @VisibleForTesting
    IExpertCallBack getExpertsCallBack() {
        return mExpertsCallBack;
    }

    private IExpertCallBack mExpertsCallBack = new IExpertCallBack() {
        @Override
        public void expertsIsLoaded(@NonNull List<Expert> expertList) {
            List<ClientSelectedExpert> list = new ArrayList<>();
            if (expertList.size() != 0) {
                for (Expert expert : expertList) {
                    list.add(new ClientSelectedExpert(expert.getId(), expert.getAbbreviatedFullName(), expert.getExpertPhotoUri()));
                }
                ClientExpertItem item = new ClientExpertItem("Лучшие эксперты", list);
                mRowTypes.add(0, item);
                mLiveData.postValue(mRowTypes);
                mIsBestExpertLoading.postValue(false);
                loadActiveAppointments();
            }
        }
    };

    private IClientAppointmentCallback mAppointmentStatus = new IClientAppointmentCallback() {
        @Override
        public void clientsAppointmentsIsLoaded(List<Appointment> appointmentList, List<Expert> expertList) {
            mRowTypes = new ArrayList<>(mRowTypes.subList(0, 1));
            mIsActiveAppointmentLoading.postValue(false);
            if (appointmentList.size() != 0 && expertList.size() != 0) {
                List<ClientAppointment> activeAppointments = convertAppointmentToRowType(appointmentList, expertList);
                if (activeAppointments != null) {
                    ClientActiveAppointmentsItem clientActiveAppointmentsItem = new ClientActiveAppointmentsItem(activeAppointments);
                    mRowTypes.add(clientActiveAppointmentsItem);
                }
            } else {
                mRowTypes.add(new ClientNoAppointmentItem());
            }
            mLiveData.postValue(mRowTypes);

        }

        @Override
        public void clientAppointmentIsDeleted(Boolean isDeleted) {
            if (isDeleted) {
                loadActiveAppointments();
            }
        }
    };

    public AppointmentsViewModel(
            @NonNull AppointmentsInteractor iteractor,
            @NonNull ExpertsIteractor expertsIteractor, @NonNull Executor executor) {
        mAppointmentsInteractor = iteractor;
        mExpertsIteractor = expertsIteractor;
        mExecutor = executor;
    }

    public void loadClientExperts() {
        mIsBestExpertLoading.setValue(true);
        mExecutor.execute(() -> {
            mExpertsIteractor.loadAllExperts(mExpertsCallBack);
        });
    }

    public void loadActiveAppointments() {
        mIsActiveAppointmentLoading.setValue(true);
        mExecutor.execute(() -> {
            mAppointmentsInteractor.loadClientsAppointments(2, mAppointmentStatus);
        });
    }

    public LiveData<List<RowType>> getLiveData() {
        return mLiveData;
    }

    public void deleteClientAppointment(long appointmentId) {
        mExecutor.execute(() -> {
            mAppointmentsInteractor.deleteClientAppointment(appointmentId, mAppointmentStatus);
        });
    }

    public LiveData<Boolean> getIsBestExpertLoading() {
        return mIsBestExpertLoading;
    }

    public LiveData<Boolean> getIsActiveAppointmentLoading() {
        return mIsActiveAppointmentLoading;
    }


    private List<ClientAppointment> convertAppointmentToRowType(@NonNull List<Appointment> appointments, @NonNull List<Expert> experts) {
        //if (appointments.size() != 0 && experts.size() != 0) {
        List<ClientAppointment> data = new ArrayList<>();
        Map<Integer, String> expertsName = new HashMap<>();
        Map<Integer, String> expertsProfession = new HashMap<>();
        Map<Integer, String> expertsNumber = new HashMap<>();

        for (int i = 0; i < appointments.size(); i++) {
            for (int j = 0; j < experts.size(); j++) {
                if (appointments.get(i).getExpertId() == experts.get(j).getId()) {
                    expertsName.put(i, experts.get(j).getFullName());
                    expertsProfession.put(i, experts.get(j).getProfession());
                    expertsNumber.put(i, experts.get(j).getPhoneNumber());
                }
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            data.add(new ClientAppointment(
                    appointment.getId(),
                    expertsProfession.get(i),
                    expertsName.get(i),
                    expertsNumber.get(i),
                    appointment.getServiceName(),
                    appointment.getStringTime(),
                    appointment.getSessionDuration(),
                    appointment.getCost(),
                    appointment.getLocation(),
                    appointment.getStringDate()));
        }

        return data;
    }
}

