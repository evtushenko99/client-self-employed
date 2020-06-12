package com.example.client_self_employed.presentation.expert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.notification.Constants;
import com.google.android.material.textfield.TextInputEditText;

public class ExpertDetailedAppointmentFragment extends Fragment {
    private Appointment mAppointment;

    public static ExpertDetailedAppointmentFragment newInstance(Appointment appointment) {

        Bundle args = new Bundle();
        args.putParcelable(Constants.APPOINTMENT_UID, appointment);
        ExpertDetailedAppointmentFragment fragment = new ExpertDetailedAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mAppointment = getArguments().getParcelable(Constants.APPOINTMENT_UID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expert_detailed_appoinment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //
        inflater.inflate(R.menu.delete_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAppointment != null) {
            TextInputEditText mServiceName = view.findViewById(R.id.expert_detailed_appointment_name);
            mServiceName.setText(mAppointment.getServiceName());
            TextInputEditText mlocation = view.findViewById(R.id.expert_detailed_appointment_location);
            mlocation.setText(mAppointment.getLocation());
            TextInputEditText mCost = view.findViewById(R.id.expert_detailed_appointment_cost);
            mCost.setText(String.valueOf(mAppointment.getCost()));
            TextInputEditText mDate = view.findViewById(R.id.expert_detailed_appointment_date);
            mDate.setText(mAppointment.getStringDate());
            TextInputEditText mTime = view.findViewById(R.id.expert_detailed_appointment_time);
            mTime.setText(mAppointment.getStringTime());
            TextInputEditText mDuration = view.findViewById(R.id.expert_detailed_appointment_duration);
            mDuration.setText(mAppointment.getSessionDuration());
            TextInputEditText mClientName = view.findViewById(R.id.expert_appointment_client);
            mClientName.setText("Евтушенко Максим");
        }
    }
}
