package com.example.client_self_employed.presentation.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.ActivityActiveAppointments;
import com.example.client_self_employed.presentation.adapters.items.ClientAppointmentItem;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModel;
import com.example.client_self_employed.presentation.viewmodels.AppointmentsViewModelFactory;

public class FragmentDetailedAppointment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CALL_PHONE = 0;
    public static final String SAVED_APPOINTMENT = "APPOINTMENT";
    private TextView mExpertProfessionTextView;
    private TextView mExpertNameTextView;
    private TextView mExpertPhoneNumberTextView;
    private TextView mTimeTextView;
    private TextView mDateTextView;
    private TextView mCostTextView;
    private TextView mLocationTextView;
    private TextView mAdditionalInformationTextView;
    private AppCompatButton mCancelButton;
    private ImageButton mPhoneCall;

    private AppointmentsViewModel mViewModel;
    private ClientAppointmentItem mAppointment;

    public static FragmentDetailedAppointment newInstance() {

        Bundle args = new Bundle();

        FragmentDetailedAppointment fragment = new FragmentDetailedAppointment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            mAppointment = (ClientAppointmentItem) this.getArguments().getSerializable(SAVED_APPOINTMENT);

        }
        return inflater.inflate(R.layout.fragment_detailed_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpertProfessionTextView = view.findViewById(R.id.expert_profession_detailed_appointment);
        mExpertNameTextView = view.findViewById(R.id.expert_name_detailed_appointment);
        mExpertPhoneNumberTextView = view.findViewById(R.id.expert_phone_number_detailed_appointment);
        mTimeTextView = view.findViewById(R.id.time_detailed_appointment);
        mDateTextView = view.findViewById(R.id.date_detailed_appointment);
        mCostTextView = view.findViewById(R.id.cost_detailed_appointment);
        mLocationTextView = view.findViewById(R.id.location_detailed_appointment);
        mAdditionalInformationTextView = view.findViewById(R.id.additional_information_detailed_appointment);
        mCancelButton = view.findViewById(R.id.cancel_appointment);
        view.findViewById(R.id.call_with_phone).setOnClickListener(this);
        view.findViewById(R.id.call_with_whatsapp).setOnClickListener(this);
    }

    @Override
    public void onStart() {

        mExpertProfessionTextView.setText(mAppointment.getExpertProfession());
        mExpertNameTextView.setText(mAppointment.getExpertName());
        mExpertPhoneNumberTextView.setText(mAppointment.getExpertNumber());
        mTimeTextView.setText(mAppointment.getStartTime());
        mDateTextView.setText(mAppointment.getStringDate());
        mCostTextView.setText(String.valueOf(mAppointment.getCost()));
        mLocationTextView.setText(mAppointment.getLocation());
        mAdditionalInformationTextView.setText("");
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Отмена записи");
                builder.setMessage("Вы уверены что хотите отменить запись?");
                // add a button
                builder.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mViewModel = ViewModelProviders.of(getActivity(), new AppointmentsViewModelFactory(getActivity()))
                                .get(AppointmentsViewModel.class);
                        mViewModel.deleteClientAppointment(mAppointment.getId());
                        Intent intent = new Intent(getActivity(), ActivityActiveAppointments.class);
                        startActivity(intent );
                    }
                });
                builder.setNegativeButton("Нет", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_with_phone:
                if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);

                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mExpertPhoneNumberTextView.getText().toString()));
                    startActivity(intent);
                }
                break;
            case R.id.call_with_whatsapp:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + mExpertPhoneNumberTextView.getText().toString() + "&text=" + "This is my text to send."));
                getActivity().startActivity(intent);
                break;

        }
    }
}
