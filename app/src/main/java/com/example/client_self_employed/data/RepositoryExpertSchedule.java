package com.example.client_self_employed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IExpertScheduleCallback;
import com.example.client_self_employed.domain.IExpertScheduleRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryExpertSchedule implements IExpertScheduleRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceAppointment = mReference.child("appointments");
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");
    private List<Appointment> mAppointments = new ArrayList<>();

    public RepositoryExpertSchedule() {

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

                    // dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }

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

}
