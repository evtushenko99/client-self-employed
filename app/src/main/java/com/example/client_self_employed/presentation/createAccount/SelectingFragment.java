package com.example.client_self_employed.presentation.createAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;

public class SelectingFragment extends Fragment implements View.OnClickListener {
    public static SelectingFragment newInstance() {
        return new SelectingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selecting_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.select_expert).setOnClickListener(this);
        view.findViewById(R.id.select_client).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.select_expert) {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_activity_container, CreateExpertAccount.newInstance())
                    .commit();
        } else {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.create_activity_container, CreateClientAccountFragment.newInstance())
                    .commit();
        }
    }
}
