package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointment;
import com.example.client_self_employed.presentation.clicklisteners.BestExpertItem;
import com.example.client_self_employed.presentation.clicklisteners.FindExpertButton;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItem;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModel;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModelFactory;

import java.util.Objects;


public class FragmentsActiveAppointments extends Fragment {
    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;

    private static final String SAVED_APPOINTMENT = "APPOINTMENT";
    private static final String SAVED_HOLDER_POSITION = "POSITION";

    private RecyclerView mRecyclerView;
    private AppointmentsViewModel mViewModel;
    private long mExpertId;
    private int mPosition;

    public static FragmentsActiveAppointments newInstance() {
        Bundle args = new Bundle();
        FragmentsActiveAppointments fragment = new FragmentsActiveAppointments();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            mPosition = this.getArguments().getInt(SAVED_HOLDER_POSITION);
        }
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointments_with_experts_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.list_of_active_appointments_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(new AdapterClientsAppointments(null,
                new ActiveAppointment() {
                    @Override
                    public void onAppointmentsItemClickListener(ClientAppointment appointment, int holderPosition) {
                        FragmentDetailedAppointment fragmentDetailedAppointment = new FragmentDetailedAppointment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(SAVED_APPOINTMENT, appointment);
                        bundle.putInt(SAVED_HOLDER_POSITION, holderPosition);
                        fragmentDetailedAppointment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction()

                                .replace(R.id.fragment_host_appointments_with_experts, fragmentDetailedAppointment)
                                .addToBackStack("active_appointments")
                                .commit();

                    }
                },
                new NewRecordToBestExpertButtonItem() {
                    @Override
                    public void onButtonItemClickListener() {
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
                    }
                },
                new BestExpertItem() {
                    @Override
                    public void onExpertItemClickListener(long expertId) {

                    }
                },
                new FindExpertButton() {
                    @Override
                    public void onFindExpertButton() {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_host_appointments_with_experts, FragmentFindExpert.newInstance())
                                .addToBackStack("active_appointments")
                                .commit();
                    }
                }
        ));
        setupMvvm();
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
