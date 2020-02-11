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
import androidx.transition.Fade;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.clicklisteners.AppointmentsItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.ExpertsItemClickListener;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;
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
                        new AppointmentsItemClickListener() {
                            @Override
                            public void onAppointmentsItemClickListener(ClientAppointmentItem appointment, int holderPosition) {
                                FragmentDetailedAppointment fragmentDetailedAppointment = new FragmentDetailedAppointment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(SAVED_APPOINTMENT, appointment);
                                bundle.putInt(SAVED_HOLDER_POSITION, holderPosition);
                                fragmentDetailedAppointment.setArguments(bundle);

                                Fade enterFade = new Fade();
                                // enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
                                enterFade.setDuration(FADE_DEFAULT_TIME);
                                fragmentDetailedAppointment.setEnterTransition(enterFade);
                                fragmentDetailedAppointment.setExitTransition(enterFade);

                                //intent.putExtra(SAVED_APPOINTMENT, appointment);

                                // int fade = TransitionInflater.from(getActivity()).inflateTransition(R.transition.fade).get;

                                getActivity().getSupportFragmentManager().beginTransaction()

                                        .replace(R.id.fragment_host_appointments_with_experts, fragmentDetailedAppointment)
                                        .addToBackStack("active_appointments")
                                        // .setTransition(fade)
                                        .commit();

                            }
                        }, new NewRecordToBestExpertButtonItemClickListener() {
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

                            /*Intent intent = new Intent(getActivity(), ActivityExpertSchedule.class);
                            intent.putExtra(Arguments.EXPERT_ID, mExpertId);
                            startActivity(intent);*/
                        } else {
                            Toast.makeText(getActivity(), "Выберите желаемого эксперта", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new ExpertsItemClickListener() {
                    @Override
                    public void onExpertItemClickListener(long expertId) {
                        mExpertId = expertId;
                    }
                })
        );
        setupMvvm();
        if (mPosition > 0) {
            deleteItem();
        }

    }

    private void deleteItem() {
        //  ((AdapterClientsAppointments) mRecyclerView.getAdapter()).deleteItem(mPosition);
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

    @Override
    public void onStart() {
        super.onStart();
        // mViewModel.loadActiveAppointments();
    }

    public void uddateActiveAppointments() {
        if (mViewModel != null) {
            mViewModel.loadClientExperts();
        }
    }

}
