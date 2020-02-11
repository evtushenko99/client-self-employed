package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.Arguments;

public class FragmentFindExpert extends Fragment implements View.OnClickListener {
    private SearchView mSearchView;
    private RecyclerView mFindRecycler;
    private Button mFiltersButton;
    private ConstraintLayout mFiltersLayout;

    public static FragmentFindExpert newInstance() {

        Bundle args = new Bundle();

        FragmentFindExpert fragment = new FragmentFindExpert();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_experts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = view.findViewById(R.id.find_expert_search_view);

        mFindRecycler = view.findViewById(R.id.find_expert_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        mFindRecycler.setLayoutManager(linearLayoutManager);
        mFindRecycler.addItemDecoration(dividerItemDecoration);

        mFiltersLayout = view.findViewById(R.id.find_expert_filters_layout);

        view.findViewById(R.id.find_expert_button).setOnClickListener(this);
        view.findViewById(R.id.find_expert_filters).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_expert_button: {

                FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                Bundle bundle = new Bundle();
                bundle.putLong(Arguments.EXPERT_ID, 2);
                fragmentExpertSchedule.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, fragmentExpertSchedule)
                        .addToBackStack("active_appointments")
                        .commit();
            }
            case R.id.find_expert_filters: {
                mFiltersLayout.setVisibility(View.VISIBLE);
            }

        }
    }
}
