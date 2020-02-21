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
import com.example.client_self_employed.databinding.ActiveAppointmentsBinding;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModel;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModelFactory;


public class FragmentActiveAppointments extends Fragment {

    private RecyclerView mRecyclerView;
    private AppointmentsViewModel mViewModel;

    private long mExpertId;

    public static FragmentActiveAppointments newInstance() {
        Bundle args = new Bundle();
        FragmentActiveAppointments fragment = new FragmentActiveAppointments();
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
        ActiveAppointmentsBinding binding = ActiveAppointmentsBinding.inflate(inflater, container, false);
        mViewModel = ViewModelProviders.of(requireActivity(), new AppointmentsViewModelFactory(requireActivity()))
                .get(AppointmentsViewModel.class);
        mViewModel.loadClientExperts();
        binding.setActiveAppointmentViewModel(mViewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle(R.string.title_fragment_active_appointments);

        mRecyclerView = view.findViewById(R.id.list_of_active_appointments_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new AdapterClientsAppointments(null,
                (appointment, position) -> {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_host_appointments_with_experts, FragmentDetailedAppointment.newInstance(appointment.getId(), appointment.getExpertId(), position))
                            .addToBackStack("active_appointments")
                            .commit();
                },
                () -> {
                    if (mExpertId != 0) {
                        FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                        Bundle bundle = new Bundle();
                        bundle.putLong(Arguments.EXPERT_ID, mExpertId);
                        fragmentExpertSchedule.setArguments(bundle);
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_host_appointments_with_experts, fragmentExpertSchedule)
                                .addToBackStack("active_appointments")
                                .commit();
                    } else {
                        Toast.makeText(getActivity(), "Выберите желаемого эксперта", Toast.LENGTH_LONG).show();
                    }
                },
                expertId -> mExpertId = expertId,
                () -> requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit(),
                getResources()
        ));

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.find_expert_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_find_expert: {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
            case R.id.menu_settings: {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentAccountSettings.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}