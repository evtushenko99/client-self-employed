package com.example.client_self_employed.presentation.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Expert;

public class DialogFragmentDetailedExpert extends DialogFragment {
    private Expert mExpert;

    private DialogFragmentDetailedExpert(Expert expert) {
        mExpert = expert;
    }

    public static DialogFragmentDetailedExpert newInstance(Expert expert) {
        return new DialogFragmentDetailedExpert(expert);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_expert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.fragment_detailed_expert_profession)).setText(mExpert.getProfession());
        ((TextView) view.findViewById(R.id.fragment_detailed_expert_name)).setText(mExpert.getFullName());
        ((TextView) view.findViewById(R.id.fragment_detailed_expert_age)).setText((String.valueOf(mExpert.getAge())));
        ((TextView) view.findViewById(R.id.fragment_detailed_email)).setText(mExpert.getEmail());
        //  String work_experience = view.getResources().getQuantityString(R.plurals.declination_of_years, mExpert.getWorkExperience(), mExpert.getWorkExperience());
        String work_experience = mExpert.getWorkExperience();
        ((TextView) view.findViewById(R.id.fragment_detailed_work_experience)).setText(work_experience);
        ((TextView) view.findViewById(R.id.fragment_detailed_phone_number)).setText(mExpert.getPhoneNumber());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
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
