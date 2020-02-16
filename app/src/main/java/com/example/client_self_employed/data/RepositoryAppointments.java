package com.example.client_self_employed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.domain.IAppointmentsCallback;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
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
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryAppointments implements IAppointmentsRepository {
    private DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mDatabaseReferenceAppointment = mReference.child("appointments");
    private DatabaseReference mDatabaseReferenceExpert = mReference.child("experts");
    private DatabaseReference mDatabaseReferenceClient = mReference.child("clients");

    public RepositoryAppointments() {
    }

    @Override
    public void deleteClientsAppointment(@NonNull Long id, IClientAppointmentCallback dataStatus) {

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

    @Override
    public void loadClientsAppointments(@NonNull Long clientId, IAppointmentsCallback callback) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.CLIENT_ID).equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Appointment> appointments = new ArrayList<>();
                        List<Long> epertId = new ArrayList<>();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            long expertId = keyMode.getValue(Appointment.class).getExpertId();
                            if (!epertId.contains(expertId)) {
                                epertId.add(expertId);
                            }
                            appointments.add(keyMode.getValue(Appointment.class));
                        }
                        Collections.sort(appointments);
                        callback.onAppointmentCallback(appointments, epertId);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onAppointmentCallback(null, null);
                        Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
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
        mDatabaseReferenceClient.child(String.valueOf(client.getId())).setValue(client);
    }

    @Override
    public void uploadReview(Review review) {
        // mDatabaseReferenceAppointment.child(String.valueOf(review.getId())).setValue(review);
    }


}
