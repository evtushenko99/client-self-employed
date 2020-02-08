package com.example.client_self_employed.presentation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.fragments.FragmentDetailedAppointment;

public class ActivityDetailedAppointment extends AppCompatActivity {
    public static final String SAVED_APPOINTMENT = "APPOINTMENT";
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_appointment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            ClientAppointmentItem appointment = (ClientAppointmentItem) b.getSerializable(SAVED_APPOINTMENT);
            String phoneNumber = getIntent().getExtras().getString(Arguments.EXPERT_NUMBER);
            if (appointment != null) {
                FragmentDetailedAppointment fragmentDetailedAppointment = new FragmentDetailedAppointment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(SAVED_APPOINTMENT, appointment);
                bundle.putString(Arguments.EXPERT_NUMBER, phoneNumber);
                fragmentDetailedAppointment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_detailed_appointment, fragmentDetailedAppointment)
                        .commit();
            } else {
                Toast.makeText(this, "Запись не дошла", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: Detailed");

        super.onStop();

    }
}
