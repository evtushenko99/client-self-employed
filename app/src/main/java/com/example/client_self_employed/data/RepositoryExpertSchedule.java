package com.example.client_self_employed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.data.model.FirebaseExpert;
import com.example.client_self_employed.domain.IExpertRepository;
import com.example.client_self_employed.domain.IExpertScheduleStatus;
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

public class RepositoryExpertSchedule implements IExpertRepository {
    private DatabaseReference mDatabaseReferenceAppointment;
    private DatabaseReference mDatabaseReferenceExpert;
    private List<Appointment> mAppointments;

    public RepositoryExpertSchedule() {
        mDatabaseReferenceAppointment = FirebaseDatabase.getInstance().getReference().child("appointments");
        mDatabaseReferenceExpert = FirebaseDatabase.getInstance().getReference().child("experts");
        mAppointments = new ArrayList<>();
    }


    private void loadExpertName(long expertId, IExpertScheduleStatus expertScheduleStatus) {

        mDatabaseReferenceExpert.orderByChild(FirebaseExpert.Fields.ID).equalTo(expertId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = null;
                        for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                            Expert expert = keyExpert.getValue(Expert.class);
                            name = expert.getFirstName();
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
    public void loadExpertSchedule(long expertId, IExpertScheduleStatus expertScheduleStatus) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.EXPERT_ID).equalTo(expertId).getRef()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mAppointments.clear();

                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            Appointment appointment = keyMode.getValue(Appointment.class);
                            mAppointments.add(appointment);
                        }
                        Collections.sort(mAppointments);

                        loadExpertName(expertId, expertScheduleStatus);
                    }


                    // dataStatus.clientsAppointmentsIsLoaded(mAppointments, mExperts);
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
                    }
                });
    }
}
