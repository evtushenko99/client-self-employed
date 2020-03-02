package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.ActiveAppointmentsBinding;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterHomeScreen;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenViewModel;


public class FragmentHomeScreen extends Fragment {

    private RecyclerView mRecyclerView;
    private HomeScreenViewModel mViewModel;

    private long mExpertId;

    public static FragmentHomeScreen newInstance() {
        Bundle args = new Bundle();
        FragmentHomeScreen fragment = new FragmentHomeScreen();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getHomeScreenModelFactory())
                .get(HomeScreenViewModel.class);
        mViewModel.loadClientExperts();
        ActiveAppointmentsBinding binding = ActiveAppointmentsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setActiveAppointmentViewModel(mViewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list_of_active_appointments_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new AdapterHomeScreen(null,
                (appointment, position) -> {
                    TextView textView = view.findViewById(R.id.item_expert_name);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                            .replace(R.id.fragment_container, FragmentDetailedAppointment.newInstance(appointment.getId(), appointment.getExpertId(), position))
                            .addToBackStack("active_appointments")
                            .commit();
                },
                (expertId) -> {
                    if (expertId != 0) {
                        FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                        Bundle bundle = new Bundle();
                        bundle.putLong(Arguments.EXPERT_ID, expertId);
                        fragmentExpertSchedule.setArguments(bundle);
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                                .replace(R.id.fragment_container, fragmentExpertSchedule)
                                .addToBackStack("active_appointments")
                                .commit();
                    } else {
                        Toast.makeText(getActivity(), "Выберите желаемого эксперта", Toast.LENGTH_LONG).show();
                    }
                },
                () -> requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit(),
                getResources()
        ));
        mViewModel.getErrors().observe(getViewLifecycleOwner(), message -> {
            CustomToast.makeToast(requireActivity(), message, view);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
                        .replace(R.id.fragment_container, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
            case R.id.menu_settings: {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentAccountSettings.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
