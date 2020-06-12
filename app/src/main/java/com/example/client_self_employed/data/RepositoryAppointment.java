package com.example.client_self_employed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.client_self_employed.R;
import com.example.client_self_employed.data.model.FirebaseAppoinment;
import com.example.client_self_employed.domain.IAppointmentsCallback;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.ILoadOneAppointmentCallback;
import com.example.client_self_employed.domain.INewAppoinmentCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.domain.model.Review;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RepositoryAppointment implements IAppointmentRepository {
    private final FirebaseDatabase mReference = FirebaseDatabase.getInstance();
    private final DatabaseReference mDatabaseReferenceAppointment = mReference.getReference().child("appointments");
    private final DatabaseReference mDatabaseReferenceExpert = mReference.getReference().child("experts");
    private final DatabaseReference mDatabaseReferenceClient = mReference.getReference().child("clients");
    private final DatabaseReference mDatabaseConnection = mReference.getReference(".info/connected");

    private ResourceWrapper mResourceWrapper;

    public RepositoryAppointment(ResourceWrapper resourceWrapper) {
        mResourceWrapper = resourceWrapper;
    }

    @Override
    public void deleteClientsAppointment(@NonNull Long id, IClientAppointmentCallback callback) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID).equalTo(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot keyExpert : dataSnapshot.getChildren()) {
                            Appointment appointment = keyExpert.getValue(Appointment.class);
                            appointment.setClientId("0");
                            mDatabaseReferenceAppointment.child(String.valueOf(appointment.getId())).setValue(appointment)
                                    .addOnCompleteListener(task -> callback.clientAppointmentIsDeleted(true));
                            callback.message(mResourceWrapper.getString(R.string.appointment_successful_deleted));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled() deleteClientsAppointment = [" + databaseError.getMessage() + "]";
                        callback.message(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void loadClientActiveAppointments(@NonNull String clientId, IAppointmentsCallback callback) {

        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.CLIENT_ID)
                .equalTo(clientId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Appointment> appointments = new ArrayList<>();
                        List<String> expertId = new ArrayList<>();
                        for (DataSnapshot keyMode : dataSnapshot.getChildren()) {
                            String id = keyMode.getValue(Appointment.class).getExpertId();
                            if (!expertId.contains(id)) {
                                expertId.add(id);
                            }
                            appointments.add(keyMode.getValue(Appointment.class));
                        }
                        Collections.sort(appointments);
                        callback.onAppointmentCallback(appointments, expertId);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadClientActiveAppointments: " + databaseError.getMessage();
                        callback.onErrorLoadingActiveAppointments(error);
                        Log.d(TAG, error);
                    }
                });
    }

    @Override
    public void updateAppointmentRating(@NonNull Long appoinmentId, float rating, ILoadOneAppointmentCallback callback) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID)
                .equalTo(appoinmentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appoinmentSnapshot : dataSnapshot.getChildren()) {
                            Appointment appointment = appoinmentSnapshot.getValue(Appointment.class);
                            if (appointment != null) {
                                appointment.setRating(rating);
                                String appointmentId = String.valueOf(appointment.getId());
                                mDatabaseReferenceAppointment.child(appointmentId)
                                        .setValue(appointment)
                                        .addOnCompleteListener(task -> callback.onUpdateCallback(true));
                                callback.messageLoadOneAppointment(mResourceWrapper.getString(R.string.successful_update));
                            } else {
                                callback.messageLoadOneAppointment(mResourceWrapper.getString(R.string.cant_load_one_appointment));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void loadOneAppointment(@NonNull Long appointmentId, ILoadOneAppointmentCallback callback) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID)
                .equalTo(appointmentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appoinmentSnapshot : dataSnapshot.getChildren()) {
                            Appointment appointment = appoinmentSnapshot.getValue(Appointment.class);
                            if (appointment != null) {
                                callback.oneAppointmentIsLoaded(appointment);
                            } else {
                                callback.messageLoadOneAppointment(mResourceWrapper.getString(R.string.cant_load_one_appointment));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled loadOneAppointment = {" + databaseError.getMessage() + "]";
                        callback.messageLoadOneAppointment(error);
                        Log.d(TAG, error);
                    }
                });

    }


    @Override
    public void uploadAppointment(Appointment appointment, INewAppoinmentCallback callback) {
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

    @Override
    public void updateAppointmentNotification(Long appointmentId, boolean isNotification, ILoadOneAppointmentCallback callback) {
        mDatabaseReferenceAppointment.orderByChild(FirebaseAppoinment.Fields.ID)
                .equalTo(appointmentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot appoinmentSnapshot : dataSnapshot.getChildren()) {
                            Appointment appointment = appoinmentSnapshot.getValue(Appointment.class);
                            if (appointment != null) {
                                appointment.setNotification(!isNotification);
                                String appointmentId = String.valueOf(appointment.getId());
                                mDatabaseReferenceAppointment.child(appointmentId)
                                        .setValue(appointment)
                                        .addOnCompleteListener(task -> callback.onUpdateCallback(true));
                            } else {
                                callback.messageLoadOneAppointment(mResourceWrapper.getString(R.string.cant_load_one_appointment));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String error = "onCancelled updateAppointmentNotification = {" + databaseError.getMessage() + "]";
                        callback.messageLoadOneAppointment(error);
                        Log.d(TAG, error);
                    }
                });
    }


}
