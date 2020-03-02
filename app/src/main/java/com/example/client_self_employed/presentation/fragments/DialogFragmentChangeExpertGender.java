package com.example.client_self_employed.presentation.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModel;

public class DialogFragmentChangeExpertGender extends DialogFragment {
    private final String mOldGender;
    private int mOldPosition = -1;
    private int mNewPosition = -1;
    private AccountViewModel mAccountViewModel;
    private String[] genders;


    public static DialogFragmentChangeExpertGender newInstance(String oldGender) {
        return new DialogFragmentChangeExpertGender(oldGender);
    }

    private DialogFragmentChangeExpertGender(String oldGender) {
        mOldGender = oldGender;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        genders = requireContext().getResources().getStringArray(R.array.gender);
        for (int i = 0; i < genders.length; i++) {
            if (genders[i].equals(mOldGender)) {
                mOldPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mAccountViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getAccountViewModelFactory())
                .get(AccountViewModel.class);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.dialog_fragment_change_expert_gender)
                .setSingleChoiceItems(genders, mOldPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewPosition = which;
                    }
                })

                .setPositiveButton(R.string.detailed_fragment_change_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOldPosition != mNewPosition && mNewPosition != -1) {
                            mAccountViewModel.updateClientGender(genders[mNewPosition]);
                        }
                    }
                })
                .setNegativeButton(R.string.detailed_fragment_dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return alertDialog.create();
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
}
