package com.example.client_self_employed.presentation.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.adapters.AdapterClientExpertSchedule;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModel;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;

public class FragmentExpertSchedule extends Fragment {

    private TextView mExpertNameTitle;
    private RecyclerView mScheduleRecycler;
    private ExpertScheduleViewModel mScheduleViewModel;

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
        View view = inflater.inflate(R.layout.fragment_expert_schedule, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpertNameTitle = view.findViewById(R.id.schedule_expert_name);

        mScheduleRecycler = view.findViewById(R.id.schedule_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        mScheduleRecycler.setLayoutManager(layoutManager);
        //mScheduleRecycler.addItemDecoration(dividerItemDecoration);
        if (this.getArguments() != null) {
            long id = this.getArguments().getLong(Arguments.EXPERT_ID);
            setupScheduleMVVM(id);
        } else {
            Toast.makeText(getActivity(), "ID эксперта не дошло", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupScheduleMVVM(long id) {
        mScheduleViewModel = ViewModelProviders.of(getActivity(), new ExpertScheduleViewModelFactory(getActivity()))
                .get(ExpertScheduleViewModel.class);
        mScheduleViewModel.loadExpertSchedule(this.getArguments().getLong(Arguments.EXPERT_ID));
        mScheduleViewModel.getExpertName().observe(this, expertName -> {
            mExpertNameTitle.setText(expertName);
        });
        mScheduleViewModel.getExpertSchedule().observe(this, expertSchedule -> {
            mScheduleRecycler.setAdapter(new AdapterClientExpertSchedule(expertSchedule, mScheduleViewModel.getResourceWrapper()));
        });


    }
}
