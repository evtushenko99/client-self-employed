package com.example.client_self_employed.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.fragments.FragmentExpertSchedule;

public class ActivityExpertSchedule extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_schedule);
        long expertId = getIntent().getExtras().getLong(Arguments.EXPERT_ID);
        if (expertId != -1) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.schedule_container, FragmentExpertSchedule.newInstance(expertId))
                    .commit();
        }
    }
}
