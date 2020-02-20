package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.ILoadExpertPhotoCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryExperts implements IExpertsRepository {

    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");
    private StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceAppointment = mReference.child("appointments");
    private List<Appointment> mAppointments = new ArrayList<>();

    public RepositoryExperts() {

    }

    private void loadExpertName(long expertId, IExpertScheduleCallback expertScheduleStatus) {
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
                        expertScheduleStatus.scheduleIsLoaded(mAppointments, name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }

    @Override
    public void loadAllExperts(IExpertCallBack callBack) {
        List<Expert> experts = new ArrayList<>();
        mDatabaseReferenceExpert.addListenerForSingleValueEvent(new ValueEventListener() {
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

            }
        });
    }

    @Override
    public void loadExpertsNameForActiveAppointments(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback dataStatus) {
        if (expertsId.size() == 0) {
            dataStatus.clientsAppointmentsIsLoaded(activeAppointment, new ArrayList<>());
        } else {
            for (Long expertId : expertsId) {
                mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID).equalTo(expertId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Expert> experts = new ArrayList<>();
                                for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                                    Expert expert = keyExpert.getValue(Expert.class);
                                    if (!experts.contains(expert)) {
                                        experts.add(expert);
                                    }
                                }
                                if (experts.size() == expertsId.size()) {
                                    dataStatus.clientsAppointmentsIsLoaded(activeAppointment, experts);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
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
                        mAppointments.clear();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            Appointment appointment = keyMode.getValue(Appointment.class);
                            mAppointments.add(appointment);
                        }
                        Collections.sort(mAppointments);
                        loadExpertName(expertId, expertScheduleCallback);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
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
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
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

                    }
                });
    }

    @Override
    public void loadNewExpertPhoto(@NonNull long expertId, String newExpertPhoto, ILoadExpertPhotoCallback callback) {

    }

    private void loadExpertsPhoto(List<Expert> experts, IExpertCallBack callBack) {
        mStorageReference.child("expert_" + experts.get(0).getId() + ".jpg").getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        experts.get(0).setExpertPhotoUri(task.getResult().toString());
                        callBack.expertsIsLoaded(experts);
                    }
                });

    }
}
