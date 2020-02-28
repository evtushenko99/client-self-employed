package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.IExpertsCallBack;
import com.example.client_self_employed.domain.ILoadExpertPhotoCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryExpert implements IExpertRepository {
    private final FirebaseDatabase mReference = FirebaseDatabase.getInstance();
    private final DatabaseReference mDatabaseReferenceAppointment = mReference.getReference().child("appointments");
    private final DatabaseReference mDatabaseReferenceExpert = mReference.getReference().child("experts");
    private final DatabaseReference mDatabaseConnection = mReference.getReference(".info/connected");
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

    private ResourceWrapper mResourceWrapper;

    public RepositoryExpert(ResourceWrapper resourceWrapper) {
        mResourceWrapper = resourceWrapper;
    }

    private void loadExpertName(long expertId, List<Appointment> appointments, IExpertScheduleCallback expertScheduleStatus) {
        mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID)
                .equalTo(expertId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = null;
                        for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                            Expert expert = keyExpert.getValue(Expert.class);
                            name = expert.getFullName();
                        }
                        expertScheduleStatus.scheduleIsLoaded(appointments, name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadExpertName = [" + databaseError.getMessage() + "]";
                        expertScheduleStatus.errorOnWorkWithExpertSchedule(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void loadAllExperts(IExpertsCallBack callBack) {
        List<Expert> experts = new ArrayList<>();
        mDatabaseReferenceExpert
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot expertKey : dataSnapshot.getChildren()) {
                            Expert expert = expertKey.getValue(Expert.class);
                            experts.add(expert);
                        }
                        loadExpertsPhoto(experts, callBack);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadAllExperts: " + databaseError.getMessage();
                        callBack.errorLoadingExperts(error);
                        Log.d(TAG, error);
                    }
                });
    }


    @Override
    public void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback callback) {

        if (expertsId.size() == 0) {
            callback.clientsAppointmentsIsLoaded(activeAppointment, new ArrayList<>());
        } else {
            List<Expert> experts = new ArrayList<>();
            for (Long expertId : expertsId) {
                mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID).equalTo(expertId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                long count = dataSnapshot.getChildrenCount();

                                for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                                    Expert expert = keyExpert.getValue(Expert.class);
                                    if (!experts.contains(expert)) {
                                        experts.add(expert);
                                    }
                                }
                                if (experts.size() == expertsId.size()) {
                                    callback.clientsAppointmentsIsLoaded(activeAppointment, experts);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                String error = "onCancelled loadExpertsNameForActiveAppointments = [" + databaseError.getMessage() + "]";
                                callback.errorOnLoadingClientExperts(error);
                                Log.d(TAG, error);
                            }
                        });

            }
        }


    }

    @Override
    public void loadExpertSchedule(long expertId, IExpertScheduleCallback expertScheduleCallback) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.EXPERT_ID)
                .equalTo(expertId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Appointment> appointments = new ArrayList<>();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            Appointment appointment = keyMode.getValue(Appointment.class);
                            appointments.add(appointment);
                        }
                        Collections.sort(appointments);
                        loadExpertName(expertId, appointments, expertScheduleCallback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadExpertSchedule = [" + databaseError.getMessage() + "]";
                        expertScheduleCallback.errorOnWorkWithExpertSchedule(error);
                        Log.d(TAG, error);
                    }
                });
    }

    /**
     * Запись нового клиента на свободгую запиь клиента
     */
    @Override
    public void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleCallback expertScheduleCallback) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID)
                .equalTo(appointmentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                            Appointment appointment = keyExpert.getValue(Appointment.class);
                            if (appointment != null) {
                                appointment.setClientId(clientId);
                                String appointmentId = String.valueOf(appointment.getId());
                                mDatabaseReferenceAppointment.child(appointmentId)
                                        .setValue(appointment)
                                        .addOnCompleteListener(task -> expertScheduleCallback.newAppointment(true));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateExpertAppointment = [" + databaseError.getMessage() + "]";
                        expertScheduleCallback.errorOnWorkWithExpertSchedule(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void loadOneExpert(@NonNull long expertId, ILoadOneExpertCallback callback) {
        mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID)
                .equalTo(expertId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot expertSnapshot : dataSnapshot.getChildren()) {
                            Expert expert = expertSnapshot.getValue(Expert.class);
                            if (expert != null) {
                                callback.oneExpertIsLoaded(expert);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadOneExpert: " + databaseError.getMessage();
                        callback.errorLoadOneExpert(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void loadNewExpertPhoto(@NonNull long expertId, String newExpertPhoto, ILoadExpertPhotoCallback callback) {

    }

    private void loadExpertsPhoto(List<Expert> experts, IExpertsCallBack callBack) {
        mStorageReference.listAll()
                .addOnCompleteListener(new OnCompleteListener<ListResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ListResult> task) {
                        for (int i = 0; i < experts.size(); i++) {
                            experts.get(i).setExpertPhotoUri(task.getResult().getItems().get(i).toString());
                        }
                        //callBack.expertsIsLoaded(experts);
                    }
                });
        for (Expert expert : experts) {
            mStorageReference.child("expert_" + expert.getId() + ".jpg").getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            expert.setExpertPhotoUri(task.getResult().toString());
                            if (experts.size() == expert.getId())
                                callBack.expertsIsLoaded(experts);
                        }
                    });
        }

       /* mStorageReference.child("expert_" + experts.get(0).getId() + ".jpg").getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        experts.get(0).setExpertPhotoUri(task.getResult().toString());
                        callBack.expertsIsLoaded(experts);
                    }
                });*/

    }
}
