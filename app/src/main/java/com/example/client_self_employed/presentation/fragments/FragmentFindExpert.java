package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterFindExperts;
import com.example.client_self_employed.presentation.viewmodels.FindExpertViewModel;

public class FragmentFindExpert extends Fragment implements View.OnClickListener {
    private SearchView mSearchView;
    private RecyclerView mFindRecycler;
    private FindExpertViewModel mFindExpertViewModel;
    private Button mNewAppointmentButton;
    private String mExpertId = "-1";

    public static FragmentFindExpert newInstance() {
        return new FragmentFindExpert();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_expert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_find_expert);
        mSearchView = view.findViewById(R.id.find_expert_search_view);
        mFindRecycler = view.findViewById(R.id.find_expert_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mFindRecycler.setLayoutManager(linearLayoutManager);
        mFindRecycler.addItemDecoration(dividerItemDecoration);
        mFindRecycler.setAdapter(new AdapterFindExperts(
                null,
                expertId -> {
                    mExpertId = expertId;
                    mNewAppointmentButton.setEnabled(true);
                },
                getResources()));

        mNewAppointmentButton = view.findViewById(R.id.fragment_new_appointment_to_expert_button);
        mNewAppointmentButton.setOnClickListener(this);
        mNewAppointmentButton.setEnabled(false);
        setupMVVM();
    }

    private void setupMVVM() {
        mFindExpertViewModel = ViewModelProviders.of(getActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getFindExpertViewModelFactory())
                .get(FindExpertViewModel.class);
        mFindExpertViewModel.loadAllExperts();
        mFindExpertViewModel.getLiveData().observe(getViewLifecycleOwner(), experts -> ((AdapterFindExperts) mFindRecycler.getAdapter()).setExperts(experts));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mFindExpertViewModel.setSearchQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mFindExpertViewModel.setSearchQuery(newText);
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_new_appointment_to_expert_button) {
            if (mExpertId != "-1") {
                FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                Bundle bundle = new Bundle();
                bundle.putString(Arguments.EXPERT_ID, mExpertId);
                fragmentExpertSchedule.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentExpertSchedule)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.need_to_choose_expert), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
