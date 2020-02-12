package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModel;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModelFactory;


public class FragmentsActiveAppointments extends Fragment {
    private static final String SAVED_APPOINTMENT = "APPOINTMENT";

    private RecyclerView mRecyclerView;
    private AppointmentsViewModel mViewModel;
    private long mExpertId;

    public static FragmentsActiveAppointments newInstance() {
        Bundle args = new Bundle();
        FragmentsActiveAppointments fragment = new FragmentsActiveAppointments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active_appointments, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_fragment_active_appointments);
        mRecyclerView = view.findViewById(R.id.list_of_active_appointments_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new AdapterClientsAppointments(null,
                appointment -> {
                    FragmentDetailedAppointment fragmentDetailedAppointment = new FragmentDetailedAppointment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SAVED_APPOINTMENT, appointment);
                    fragmentDetailedAppointment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_host_appointments_with_experts, fragmentDetailedAppointment)
                            .addToBackStack("active_appointments")
                            .commit();
                },
                () -> {
                    if (mExpertId != 0) {
                        FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                        Bundle bundle = new Bundle();
                        bundle.putLong(Arguments.EXPERT_ID, mExpertId);
                        fragmentExpertSchedule.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_host_appointments_with_experts, fragmentExpertSchedule)
                                .addToBackStack("active_appointments")
                                .commit();
                    } else {
                        Toast.makeText(getActivity(), "Выберите желаемого эксперта", Toast.LENGTH_LONG).show();
                    }
                },
                expertId -> mExpertId = expertId,
                () -> getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit(),
                getResources()
        ));
        setupMvvm();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.find_expert_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_find_expert: {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupMvvm() {
        mViewModel = ViewModelProviders.of(getActivity(), new AppointmentsViewModelFactory(getActivity()))
                .get(AppointmentsViewModel.class);

        if (!mViewModel.getLiveData().hasObservers()) {
            mViewModel.loadClientExperts();
        }

        mViewModel.getLiveData().observe(this, rowTypes -> {
            ((AdapterClientsAppointments) mRecyclerView.getAdapter()).setRowTypes(rowTypes);
        });

    }

}
