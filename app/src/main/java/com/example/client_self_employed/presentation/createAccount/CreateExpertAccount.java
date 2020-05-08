package com.example.client_self_employed.presentation.createAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;

public class CreateExpertAccount extends Fragment {
    public static CreateExpertAccount newInstance() {
        return new CreateExpertAccount();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_expert_account, container, false);
        return view;
    }
}
