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
import com.example.client_self_employed.presentation.viewmodels.AccountViewModel;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

public class DialogFragmentChangeExpertPhoneNumber extends DialogFragment implements View.OnClickListener {
    private static final String EXPERT_OLD_PHONE_NUMBER = "expert phone number";
    private String mOldPhoneNumber;
    private AccountViewModel mAccountViewModel;
    private TextInputEditText mTextInputEditText;


    public static DialogFragmentChangeExpertPhoneNumber newInstance(String phoneNumber) {

        Bundle args = new Bundle();
        args.putString(EXPERT_OLD_PHONE_NUMBER, phoneNumber);
        DialogFragmentChangeExpertPhoneNumber fragment = new DialogFragmentChangeExpertPhoneNumber();
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
            mOldPhoneNumber = this.getArguments().getString(EXPERT_OLD_PHONE_NUMBER);
        }

        mAccountViewModel = ViewModelProviders.of(requireActivity(), new AccountViewModelFactory(requireContext()))
                .get(AccountViewModel.class);
        return inflater.inflate(R.layout.dialog_fragment_change_expert_phone_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextInputEditText = view.findViewById(R.id.detailed_fragment_account_client_phone_number);
        mTextInputEditText.setText(mOldPhoneNumber);
        view.findViewById(R.id.detailed_fragment_change_button).setOnClickListener(this);
        view.findViewById(R.id.detailed_fragment_dismiss_button).setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.detailed_fragment_change_button:
                String newPhoneNumber = mTextInputEditText.getText().toString();
                if (!mOldPhoneNumber.equals(newPhoneNumber)) {
                    mAccountViewModel.updateClientPhoneNumber(newPhoneNumber);
                    dismiss();
                }

                break;
            case R.id.detailed_fragment_dismiss_button:
                dismiss();
                break;
        }
    }
}
