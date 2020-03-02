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

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.DialogFragmentChangeExpertFullNameBinding;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class DialogFragmentChangeExpertFullName extends DialogFragment implements View.OnClickListener {

    private AccountViewModel mAccountViewModel;
    private TextInputEditText mLastName;
    private TextInputEditText mName;
    private TextInputEditText mSecondName;

    public static DialogFragmentChangeExpertFullName newInstance() {

        Bundle args = new Bundle();

        DialogFragmentChangeExpertFullName fragment = new DialogFragmentChangeExpertFullName();
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

        DialogFragmentChangeExpertFullNameBinding binding = DialogFragmentChangeExpertFullNameBinding.inflate(inflater, container, false);
        mAccountViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getAccountViewModelFactory())
                .get(AccountViewModel.class);
        binding.setViewModel(mAccountViewModel);
        setClickListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLastName = view.findViewById(R.id.dialog_fragment_change_full_name_client_last_name);
        mName = view.findViewById(R.id.dialog_fragment_change_full_name_client_name);
        mSecondName = view.findViewById(R.id.dialog_fragment_change_full_name_client_second_name);

        view.findViewById(R.id.detailed_fragment_dismiss_button).setOnClickListener(this);
    }

    private void setClickListeners() {
        mAccountViewModel.setOnNewClientFullNameClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAccountViewModel.getLastName().getValue().equals(mLastName.getText().toString()) ||
                        !mAccountViewModel.getName().getValue().equals(mName.getText().toString()) ||
                        !mAccountViewModel.getSecondName().getValue().equals(mSecondName.getText().toString())
                ) {
                    mAccountViewModel.updateFullName(mLastName.getText().toString(), mName.getText().toString(), mSecondName.getText().toString());
                    dismiss();
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.detailed_fragment_dismiss_button) {
            dismiss();
        }
    }
}
