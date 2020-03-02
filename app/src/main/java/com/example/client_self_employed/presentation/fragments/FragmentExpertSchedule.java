package com.example.client_self_employed.presentation.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.ExpertScheduleBinding;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterExpertSchedule;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModel;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenViewModel;

public class FragmentExpertSchedule extends Fragment {
    private RecyclerView mScheduleRecycler;
    private ExpertScheduleViewModel mScheduleViewModel;
    private HomeScreenViewModel mHomeScreenViewModel;


    public static FragmentExpertSchedule newInstance(long expertId) {
        Bundle args = new Bundle();
        args.putLong(Arguments.EXPERT_ID, expertId);
        FragmentExpertSchedule fragment = new FragmentExpertSchedule();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mScheduleViewModel = ViewModelProviders.of(getActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getExpertScheduleViewModelFactory())
                .get(ExpertScheduleViewModel.class);
        ExpertScheduleBinding binding = ExpertScheduleBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mScheduleViewModel);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.getArguments() != null) {
            long id = this.getArguments().getLong(Arguments.EXPERT_ID);
            setupScheduleMVVM(id);
            mScheduleRecycler = view.findViewById(R.id.schedule_recycler);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mScheduleRecycler.setLayoutManager(layoutManager);
            mScheduleViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
                CustomToast.makeToast(requireActivity(), message, view);
            });
        } else {
            Toast.makeText(getActivity(), "ID эксперта не дошло", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupScheduleMVVM(long id) {
        mHomeScreenViewModel = ViewModelProviders.of(getActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getHomeScreenModelFactory())
                .get(HomeScreenViewModel.class);
        mScheduleViewModel.loadExpertSchedule(id);
        mScheduleViewModel.setMoreInformation(expert -> {
            DialogFragmentDetailedExpert.newInstance(expert).show(getActivity().getSupportFragmentManager(), null);
        });
        mScheduleViewModel.getExpertSchedule().observe(getViewLifecycleOwner(), expertSchedule -> {
            mScheduleRecycler.setAdapter(new AdapterExpertSchedule(expertSchedule, mScheduleViewModel.getResourceWrapper(), (appointment, clientId) -> FragmentExpertScheduleDetailedAppointment
                    .newInstance(appointment, clientId)
                    .show(getActivity().getSupportFragmentManager(), null), mHomeScreenViewModel.getFilterInteractor()));
        });
        mScheduleViewModel.getIsChanged().observe(getViewLifecycleOwner(), isChanged -> {
            if (isChanged) {
                mHomeScreenViewModel.loadActiveAppointments();
            }
        });

    }
}
