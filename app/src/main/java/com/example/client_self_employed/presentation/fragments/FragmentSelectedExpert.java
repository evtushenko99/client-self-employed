package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;

public class FragmentSelectedExpert extends Fragment {

    public static FragmentSelectedExpert newInstance() {

        Bundle args = new Bundle();
        FragmentSelectedExpert fragment = new FragmentSelectedExpert();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_expert, container, false);
    }
}
