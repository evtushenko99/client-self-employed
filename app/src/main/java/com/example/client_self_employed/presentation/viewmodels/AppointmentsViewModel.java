package com.example.client_self_employed.presentation.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.AppointmentsIteractor;
import com.example.client_self_employed.domain.IAppointmentStatus;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.ClientButtonItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppoinmentItem;
import com.example.client_self_employed.presentation.adapters.items.ClientSelectedExportItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class AppointmentsViewModel extends ViewModel {
    private final AppointmentsIteractor mAppointmentsIteractor;
    private final Executor mExecutor;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();
    private MutableLiveData<List<RowType>> mLiveData = new MutableLiveData<>();
    private List<Appointment> mAppointmentList = new ArrayList<>();
    private List<Expert> mExpertList = new ArrayList<>();
    private List<RowType> mRowTypes = new ArrayList<>();

    private IAppointmentStatus mAppointmentStatus;

    public AppointmentsViewModel(
            @NonNull AppointmentsIteractor iteractor,
            @NonNull Executor executor) {
        mAppointmentsIteractor = iteractor;
        mExecutor = executor;
        mAppointmentStatus = new IAppointmentStatus() {
            @Override
            public void clientsAppointmentsIsLoaded(@NonNull List<Appointment> appointmentList, @NonNull List<Expert> expertList) {
                if (appointmentList.size() != 0 && expertList.size() != 0) {
                    mAppointmentList.clear();
                    mExpertList.clear();

                    mAppointmentList = appointmentList;
                    mExpertList = expertList;

                    List<RowType> activeAppointments = convertAppointmentToRowType(mAppointmentList, mExpertList);
                    if (activeAppointments != null) {
                        mRowTypes.addAll(activeAppointments);
                        mLiveData.postValue(mRowTypes);
                        mIsLoading.postValue(false);
                    }
                }else {
                    mRowTypes.add(new ClientNoAppoinmentItem());
                    mLiveData.postValue(mRowTypes);
                    mIsLoading.postValue(false);
                }


            }

            @Override
            public void clientsExpertsIsLoaded(List<Expert> expertList) {
                List<ClientSelectedExportItem> list = new ArrayList<>();

                if (expertList.size() != 0) {
                    for (Expert expert : expertList) {
                        list.add(new ClientSelectedExportItem(expert.getId(), expert.getName(), null));
                    }
                    ClientExpertItem item = new ClientExpertItem("Лучшие эксперты", list);
                    mRowTypes.add(0, item);
                    mLiveData.postValue(mRowTypes);
                }
            }

            @Override
            public void clientAppointmentIsDeleted(Boolean isDeleted) {
                if (isDeleted){
                    Log.d(TAG, "clientAppointmentIsDeleted: true");
                    mIsLoading.setValue(true);
                    mExecutor.execute(()->{
                        mAppointmentsIteractor.loadClientsAppointments(2, mAppointmentStatus);
                        mAppointmentsIteractor.loadClientsExperts(mAppointmentStatus);
                    });
                }
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdates() {

            }

            @Override
            public void dataIsDeleted() {

            }
        };
    }

    public void loadClientAppointments() {
        mIsLoading.setValue(true);
        mExecutor.execute(() -> {

            mAppointmentsIteractor.loadClientsAppointments(2, mAppointmentStatus);
            mAppointmentsIteractor.loadClientsExperts(mAppointmentStatus);
            //mAppointmentsIteractor.loadExpert(1, mAppointmentStatus);
            //List<RowType> rowTypes = new ArrayList<>();

            mRowTypes.add(new ClientButtonItem());
            if (mAppointmentList.size() != 0 && mExpertList.size() != 0) {
                List<RowType> appointmentList = convertAppointmentToRowType(mAppointmentList, mExpertList);
                mRowTypes.addAll(appointmentList);
            }


        });


    }

    public LiveData<List<RowType>> getLiveData() {
        return mLiveData;
    }

   public void deleteClientAppointment(long appoinmentId){
        mAppointmentsIteractor.deleteClientAppointment(appoinmentId, mAppointmentStatus);
   }

    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    private List<RowType> convertAppointmentToRowType(@NonNull List<Appointment> appointments, @NonNull List<Expert> experts) {
        //if (appointments.size() != 0 && experts.size() != 0) {
        List<RowType> data = new ArrayList<>();
        Map<Integer, String> expertsName = new HashMap<>();
        Map<Integer, String> expertsProfession = new HashMap<>();
        Map<Integer, String> expertsNumber = new HashMap<>();

        for (int i = 0; i < appointments.size(); i++) {
            for (int j = 0; j < experts.size(); j++) {
                if (appointments.get(i).getExpertId() == experts.get(j).getId()) {
                    expertsName.put(i, experts.get(j).getName());
                    expertsProfession.put(i, experts.get(j).getProfession());
                    expertsNumber.put(i, experts.get(j).getPhoneNumber());
                }
            }
        }
        for (int i = 0; i < appointments.size(); i++) {
            data.add(new ClientAppointmentItem(
                    appointments.get(i).getId(),
                    expertsProfession.get(i),
                    expertsName.get(i),
                    expertsNumber.get(i),
                    appointments.get(i).getServiceName(),
                    appointments.get(i).getStartTime(),
                    appointments.get(i).getSessionDuration(),
                    appointments.get(i).getCost(),
                    appointments.get(i).getLocation(),
                    appointments.get(i).getDate()));
        }
        return data;

    }
}

