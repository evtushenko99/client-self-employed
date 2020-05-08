package com.example.client_self_employed.presentation.createAccount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;

public class SelectNewGenderDialogFragment extends DialogFragment {
    private CreateClientViewModel mViewModel;
    private int mOldPosition = -1;
    private int mNewPosition = -1;
    private String[] genders;

    public static SelectNewGenderDialogFragment newInstance() {
        return new SelectNewGenderDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getCreateClientViewModelFactory())
                .get(CreateClientViewModel.class);
        genders = requireContext().getResources().getStringArray(R.array.gender);
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
                            mViewModel.setGender(genders[mNewPosition]);
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
