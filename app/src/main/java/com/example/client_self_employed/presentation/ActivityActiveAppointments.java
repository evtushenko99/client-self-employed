package com.example.client_self_employed.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;
import com.example.client_self_employed.notification.Constants;
import com.example.client_self_employed.presentation.fragments.FragmentDetailedAppointment;
import com.example.client_self_employed.presentation.fragments.FragmentHomeScreen;

public class ActivityActiveAppointments extends AppCompatActivity implements IUpdateRecyclerListener {
    private static final String SAVED_HOLDER_POSITION = "POSITION";
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: ");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_host_appointments_with_experts, FragmentHomeScreen.newInstance())
                .commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.OPEN_DETAILED_FRAGMENT)) {
                long appointmentId = extras.getLong(Constants.EXTRA_APPOINTMENT_ID);
                long expertId = extras.getLong(Constants.EXTRA_EXPERT_ID);
                FragmentDetailedAppointment fragmentDetailedAppointment = FragmentDetailedAppointment.newInstance(appointmentId, expertId, 0);
                Bundle arg = new Bundle();
                arg.putLong(Constants.UPDATE_APPOINTMENT, appointmentId);
                fragmentDetailedAppointment.setArguments(arg);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_host_appointments_with_experts, fragmentDetailedAppointment)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //getSupportFragmentManager().findFragmentById(R.id.fragment_host_appointments_with_experts).

        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Main ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void deleteAppointmentFromRecycler(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(SAVED_HOLDER_POSITION, position);
        Fragment fragment = new FragmentHomeScreen();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_appointments_with_experts, fragment)
                .commit();
    }
}
