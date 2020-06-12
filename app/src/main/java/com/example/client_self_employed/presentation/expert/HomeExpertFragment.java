package com.example.client_self_employed.presentation.expert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.presentation.Arguments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeExpertFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ExpertHomeViewModel mViewModel;

    public static HomeExpertFragment newInstance() {
        return new HomeExpertFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getExpertHomeViewModelFactory())
                .get(ExpertHomeViewModel.class);
        mViewModel.setExpertID(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_screen_expert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.expert_recycler);
        FloatingActionButton button = view.findViewById(R.id.floating_add_appointment);
        button.setOnClickListener(this);
        mViewModel.getObservableList().observe(requireActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> list) {
                startRecycler(list);
            }
        });
    }

    private void startRecycler(List<Appointment> list) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new ExpertHomeScreenAdapter(list, new ExpertAppointmentClickListener() {
            @Override
            public void onExpertAppointmentClickListener(Appointment appointment, int position) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_expert_container, ExpertDetailedAppointmentFragment.newInstance(appointment))
                        .addToBackStack(Arguments.TAG_FRAGMENT_HOME_SCREEN)
                        .commit();
            }
        }));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_add_appointment: {
                ExpertAddAppointmentDialogFragment.newInstance()
                        .show(requireActivity().getSupportFragmentManager(), null);
            }
        }
    }
}
