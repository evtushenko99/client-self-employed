package com.example.client_self_employed.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.ActivityExpertSchedule;
import com.example.client_self_employed.presentation.clicklisteners.AppointmentsItemClickListener;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.clicklisteners.ButtonItemClickListener;
import com.example.client_self_employed.presentation.ActivityDetailedAppointment;
import com.example.client_self_employed.presentation.clicklisteners.ExpertsItemClickListener;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModel;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModelFactory;

import java.util.Objects;


public class FragmentsActiveAppointments extends Fragment {
    public static final String SAVED_APPOINTMENT = "APPOINTMENT";
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
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.appointments_with_experts_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list_of_active_appointments_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        setupMvvm();


    }

    private void setupMvvm() {
        mViewModel = ViewModelProviders.of(getActivity(), new AppointmentsViewModelFactory(getActivity()))
                .get(AppointmentsViewModel.class);
        mViewModel.loadClientAppointments();
        mViewModel.getLiveData().observe(this, rowTypes -> {
            mRecyclerView.setAdapter(new AdapterClientsAppointments(rowTypes,
                            new AppointmentsItemClickListener() {
                                @Override
                                public void onAppointmentsItemClickListener(ClientAppointmentItem appointment) {
                                    Intent intent = new Intent(getActivity(), ActivityDetailedAppointment.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(SAVED_APPOINTMENT, appointment);
                                    //intent.putExtra(SAVED_APPOINTMENT, appointment);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }, new ButtonItemClickListener() {
                        @Override
                        public void onButtonItemClickListener() {
                            if (mExpertId != -1) {
                                Intent intent = new Intent(getActivity(), ActivityExpertSchedule.class);
                                intent.putExtra(Arguments.EXPERT_ID, mExpertId);
                                startActivity(intent);
                            }
                        }
                    }, new ExpertsItemClickListener() {
                        @Override
                        public void onExpertItemClickListener(long expertId) {
                            mExpertId = expertId;
                        }
                    })
            );
        });
    }


}
