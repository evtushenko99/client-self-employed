package com.example.client_self_employed.presentation.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModel;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentExpertScheduleDetailedAppointment extends DialogFragment implements View.OnClickListener {
    public static final String DETAILED_APPOINTMENT = "DETAILED APPOINTMENT";
    private static final String CLIENT_ID = "CLIENT ID";
    private Appointment mAppointment;
    private TextInputEditText mServiceName;
    private TextInputEditText mSessionDuration;
    private TextInputEditText mCost;
    private TextInputEditText mLocation;
    private TextInputEditText mDate;
    private TextInputEditText mTime;
    private TextInputEditText mAdditionalInformation;

    private AppCompatButton mNewAppointmentButton;
    private ExpertScheduleViewModel mExpertViewModel;

    private long mClientId;

    public static FragmentExpertScheduleDetailedAppointment newInstance(Appointment appointment, long clientId) {

        Bundle args = new Bundle();
        args.putParcelable(DETAILED_APPOINTMENT, appointment);
        args.putLong(CLIENT_ID, clientId);
        FragmentExpertScheduleDetailedAppointment fragment = new FragmentExpertScheduleDetailedAppointment(appointment, clientId);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentExpertScheduleDetailedAppointment(Appointment appointment, long clientId) {
        mAppointment = appointment;
        mClientId = clientId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            mAppointment = this.getArguments().getParcelable(DETAILED_APPOINTMENT);
            mClientId = this.getArguments().getLong(CLIENT_ID);

        }
        return inflater.inflate(R.layout.fragment_expert_detailed_appointments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mServiceName = view.findViewById(R.id.expert_schedule_detailed_service_name);
        mSessionDuration = view.findViewById(R.id.expert_schedule_detailed_duration);
        mCost = view.findViewById(R.id.expert_schedule_detailed_cost);
        mLocation = view.findViewById(R.id.expert_schedule_detailed_location);
        mDate = view.findViewById(R.id.expert_schedule_detailed_date);
        mTime = view.findViewById(R.id.expert_schedule_detailed_time);
        mAdditionalInformation = view.findViewById(R.id.expert_schedule_detailed_additional_information);

        view.findViewById(R.id.button_expert_detailed_appointment).setOnClickListener(this);

        mExpertViewModel = ViewModelProviders.of(getActivity(), new ExpertScheduleViewModelFactory(getActivity()))
                .get(ExpertScheduleViewModel.class);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mAppointment != null) {
                mServiceName.setText(mAppointment.getServiceName());
                mSessionDuration.setText(mAppointment.getSessionDuration());
                mCost.setText(String.valueOf(mAppointment.getCost()));
                mLocation.setText(mAppointment.getLocation());
                mDate.setText(mAppointment.getStringDate());
                mTime.setText(mAppointment.getStringTime());
                // mAdditionalInformation.setVisibility(View.VISIBLE);

            }


        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_expert_detailed_appointment: {
                mExpertViewModel.updateExpertAppointment(mAppointment.getId(), mClientId);
                dismiss();
            }
        }
    }

}
