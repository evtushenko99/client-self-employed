package com.example.client_self_employed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IAppointmentStatus;
import com.example.client_self_employed.domain.IAppointmentsRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryAppointments implements IAppointmentsRepository {
    private DatabaseReference mDatabaseReferenceAppointment;
    private DatabaseReference mDatabaseReferenceExpert;
    private List<Appointment> mAppointments;
    private List<Expert> mExperts;
    private List<Long> mExpertIds;


    public RepositoryAppointments() {
        mDatabaseReferenceAppointment = FirebaseDatabase.getInstance().getReference().child("appointments");
        mDatabaseReferenceExpert = FirebaseDatabase.getInstance().getReference().child("experts");
        mAppointments = new ArrayList<>();
        mExperts = new ArrayList<>();
        mExpertIds = new ArrayList<>();
    }


    @Override
    public void updateAppointment(long id, int clientId) {

    }

    @Override
    public void deleteClientsAppointment(long id, IAppointmentStatus dataStatus) {

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
    public void loadExpertAppointments(List<Long> expertsId, IAppointmentStatus dataStatus) {
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
                                    mExperts.add(expert);
                                }

                                if (mExperts.size() == mExpertIds.size())
                                    dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);

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
    public void loadClientsAppointments(long clientId, IAppointmentStatus dataStatus) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.CLIENT_ID).equalTo(clientId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mAppointments.clear();
                        mExpertIds.clear();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            mExpertIds.add(keyMode.getValue(Appointment.class).getExpertId());

                            Appointment appointment = keyMode.getValue(Appointment.class);
                            mAppointments.add(appointment);
                        }
                        loadExpertAppointments(mExpertIds, dataStatus);
                        // dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });

    }

    @Override
    public void loadExperts(IAppointmentStatus status) {
        List<Expert> experts = new ArrayList<>();
        mDatabaseReferenceExpert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot expertKey : dataSnapshot.getChildren()) {
                    Expert expert = expertKey.getValue(Expert.class);
                    experts.add(expert);
                }
                status.clientsExpertsIsLoaded(experts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
