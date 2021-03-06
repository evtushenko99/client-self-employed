package com.example.client_self_employed.presentation.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.ExpertScheduleDetailedAppointmentBinding;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleDetailedAppointmentViewModel;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModel;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

public class DialogFragmentDetailedAppointment extends DialogFragment {
    private static final String DETAILED_APPOINTMENT = "DETAILED APPOINTMENT";
    private static final String CLIENT_ID = "CLIENT ID";
    private Appointment mAppointment;

    private ExpertScheduleViewModel mExpertViewModel;
    private ExpertScheduleDetailedAppointmentViewModel mExpertDetailedAppointmentViewModel;

    private String mClientId;

    public static DialogFragmentDetailedAppointment newInstance(Appointment appointment, String clientId) {

        Bundle args = new Bundle();
        args.putParcelable(DETAILED_APPOINTMENT, appointment);
        args.putString(CLIENT_ID, clientId);
        DialogFragmentDetailedAppointment fragment = new DialogFragmentDetailedAppointment();
        fragment.setArguments(args);
        return fragment;
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
            mClientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            //this.getArguments().getLong(CLIENT_ID);
        }
        ExpertScheduleViewModelFactory expertScheduleViewModelFactory = ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getExpertScheduleViewModelFactory();
        mExpertViewModel = ViewModelProviders.of(getActivity(), expertScheduleViewModelFactory)
                .get(ExpertScheduleViewModel.class);
        mExpertDetailedAppointmentViewModel = ViewModelProviders.of(requireActivity())
                .get(ExpertScheduleDetailedAppointmentViewModel.class);
        setDetailedAppointmentViewModel();
        ExpertScheduleDetailedAppointmentBinding binding = ExpertScheduleDetailedAppointmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mExpertDetailedAppointmentViewModel);
        return binding.getRoot();
    }

    private void setDetailedAppointmentViewModel() {
        mExpertDetailedAppointmentViewModel.setDetailedAppointment(mAppointment);
        mExpertDetailedAppointmentViewModel.setOnNewAppointmentClickListener(v -> {
            mExpertViewModel.updateExpertAppointment(mAppointment.getId(), mClientId);
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
