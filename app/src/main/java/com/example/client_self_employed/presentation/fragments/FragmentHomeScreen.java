package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.ActiveAppointmentsBinding;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenViewModel;
import com.google.firebase.auth.FirebaseAuth;


public class FragmentHomeScreen extends Fragment {
    public static final String CLIENTID = "Client id";

    private HomeScreenViewModel mViewModel;

    private String mClientId;

    public static FragmentHomeScreen newInstance(@NonNull String clientId) {
        Bundle args = new Bundle();

        args.putString(CLIENTID, clientId);
        FragmentHomeScreen fragment = new FragmentHomeScreen();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            mClientId = savedInstanceState.getString(CLIENTID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getHomeScreenModelFactory())
                .get(HomeScreenViewModel.class);
        mViewModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //mViewModel.loadClientExperts();
        ActiveAppointmentsBinding binding = ActiveAppointmentsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.setActiveAppointmentsClickListener((appointment, position) -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, FragmentDetailedAppointment.newInstance(appointment.getId(), appointment.getExpertId(), position))
                .addToBackStack(Arguments.TAG_FRAGMENT_HOME_SCREEN)
                .commit());
        mViewModel.setNewRecordToBestExpertButtonItemClickListener((expertId) -> {
            if (expertId != "0") {
                FragmentExpertSchedule fragmentExpertSchedule = new FragmentExpertSchedule();
                Bundle bundle = new Bundle();
                bundle.putString(Arguments.EXPERT_ID, expertId);
                fragmentExpertSchedule.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragmentExpertSchedule)
                        .addToBackStack(Arguments.TAG_FRAGMENT_HOME_SCREEN)
                        .commit();
            }
        });
        mViewModel.setFindExpertButtonClickListener(() -> requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, FragmentFindExpert.newInstance())
                .addToBackStack(Arguments.TAG_FRAGMENT_HOME_SCREEN)
                .commit());

        mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.equals("")) {
                CustomToast.makeToast(requireActivity(), message, view);
                mViewModel.setMessage("");
            }
        });

    }
}
