package com.example.client_self_employed.presentation.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.client_self_employed.presentation.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignOutDialogFragment extends DialogFragment {
    private FirebaseAuth mAuth;

    public static SignOutDialogFragment newInstance() {
        return new SignOutDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Выход из аккаунта")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setMessage("Вы уверены, что хотите выйти из аккаунта?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mAuth.getCurrentUser() != null) {
                            mAuth.signOut();
                            requireActivity().startActivity(new Intent(requireActivity(), LoginActivity.class));
                        }
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
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
