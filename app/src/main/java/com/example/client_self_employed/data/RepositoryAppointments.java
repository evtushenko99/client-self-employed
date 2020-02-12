package com.example.client_self_employed.data;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IAppointmentCallback;
import com.example.client_self_employed.domain.IAppointmentsRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;
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

public class RepositoryAppointments implements IAppointmentsRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceAppointment = mReference.child("appointments");
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");
    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private StorageReference mPathReference;
    private List<Appointment> mAppointments = new ArrayList<>();
    private List<Expert> mExperts = new ArrayList<>();
    private List<Long> mExpertIds = new ArrayList<>();


    public RepositoryAppointments() {
        StorageReference storageRef = mStorage.getReference();
        //mPathReference = storageRef.child("/stars.jpg");
    }


    @Override
    public void updateAppointment(long id, int clientId) {

    }

    @Override
    public void deleteClientsAppointment(long id, IAppointmentCallback dataStatus) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID).equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                            Appointment appointment = keyExpert.getValue(Appointment.class);
                            appointment.setClientId(0);
                            mDatabaseReferenceAppointment.child(String.valueOf(appointment.getId())).setValue(appointment)
                                    .addOnCompleteListener(task -> dataStatus.clientAppointmentIsDeleted(true));
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }

    //   @Override
    public void loadExpertAppointments(List<Long> expertsId, IAppointmentCallback dataStatus) {
        mExperts.clear();
        if (mAppointments.size() == 0) {
            dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);
        } else {
            for (Long expertId : expertsId) {
                mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID).equalTo(expertId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                                    Expert expert = keyExpert.getValue(Expert.class);
                                    if (!mExperts.contains(expert)) {
                                        mExperts.add(expert);
                                    }
                                }

                                if (mExperts.size() == mExpertIds.size()) {
                                    Collections.sort(mAppointments);
                                    dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);
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
    public void loadClientsAppointments(long clientId, IAppointmentCallback dataStatus) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.CLIENT_ID).equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mAppointments.clear();
                        mExpertIds.clear();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            mExpertIds.add(keyMode.getValue(Appointment.class).getExpertId());
                            mAppointments.add(keyMode.getValue(Appointment.class));
                        }
                        loadExpertAppointments(mExpertIds, dataStatus);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });

    }

    @Override
    public void loadExperts(IAppointmentCallback status) {
        List<Expert> experts = new ArrayList<>();
        mDatabaseReferenceExpert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot expertKey : dataSnapshot.getChildren()) {
                    Expert expert = expertKey.getValue(Expert.class);
                    experts.add(expert);
                }
                status.clientsExpertsIsLoaded(experts);
                //   loadExpertsPhoto(experts, status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadExpertsPhoto(List<Expert> experts, IAppointmentCallback status) {
        List<Expert> expertsWithUri = new ArrayList<>();
        for (Expert expert : experts) {
            mPathReference.child("expert_" + expert.getId() + ".jpg").getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            expert.setExpertPhotoUri(task.getResult());

                        }
                    });
        }
        status.clientsExpertsIsLoaded(experts);

    }

    @Override
    public void uploadAppointment(Appointment appointment) {
        mDatabaseReferenceAppointment.child(String.valueOf((appointment.getId()))).setValue(appointment);
    }

    @Override
    public void uploadExpert(Expert expert) {
        mDatabaseReferenceExpert.child(String.valueOf(expert.getId())).setValue(expert);
    }

    @Override
    public void uploadClient(Client client) {
        //  mDatabaseReferenceAppointment.child(String.valueOf(client.getId())).setValue(client);
    }

    @Override
    public void uploadReview(Review review) {
        // mDatabaseReferenceAppointment.child(String.valueOf(review.getId())).setValue(review);
    }


}
