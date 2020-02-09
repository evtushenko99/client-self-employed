package com.example.client_self_employed.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.fragments.FragmentsActiveAppointments;

public class ActivityActiveAppointments extends AppCompatActivity implements IUpdateRecyclerListener {
    private static final String SAVED_HOLDER_POSITION = "POSITION";
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_host_appointments_with_experts, FragmentsActiveAppointments.newInstance())
                    .commit();

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
        Fragment fragment = new FragmentsActiveAppointments();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_appointments_with_experts, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }
}
