package com.example.client_self_employed.presentation.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.FragmentDetailedAppointmentBinding;
import com.example.client_self_employed.notification.Constants;
import com.example.client_self_employed.notification.NotificationHandler;
import com.example.client_self_employed.presentation.HomeActivity;
import com.example.client_self_employed.presentation.clicklisteners.RatingClickListeners;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IAddNotificationClickListener;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModel;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenViewModel;

public class FragmentDetailedAppointment extends Fragment {
    private static final String APPOINTMENT_ID = "appointment id";
    private static final String EXPERT_ID = "expert id";
    private static final String POSITION = "position";


    private static final int REQUEST_CALL_PHONE = 0;

    private HomeScreenViewModel mViewModel;

    private DetailedAppointmentViewModel mDetailedAppointmentViewModel;
    private long mExpertId;
    private long mAppointmentId;
    private int mPosition;

    public FragmentDetailedAppointment(long appointmentId, long expertId) {
        mExpertId = expertId;
        mAppointmentId = appointmentId;
    }

    public static FragmentDetailedAppointment newInstance(long appointmentId, long expertId, int position) {

        Bundle args = new Bundle();
        args.putLong(APPOINTMENT_ID, appointmentId);
        args.putLong(EXPERT_ID, expertId);
        args.putInt(POSITION, position);
        FragmentDetailedAppointment fragment = new FragmentDetailedAppointment(appointmentId, expertId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // setHasOptionsMenu(true);
        long updateAppointment = -1;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(POSITION);
            updateAppointment = bundle.getLong(Constants.UPDATE_APPOINTMENT, -1);
        }

        mDetailedAppointmentViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getDetailedAppointmentViewModelFactory())
                .get(DetailedAppointmentViewModel.class);
        if (updateAppointment != -1) {
            mDetailedAppointmentViewModel.setUpdateButtonText(true);
        }
        setClickListeners();
        FragmentDetailedAppointmentBinding binding = FragmentDetailedAppointmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mDetailedAppointmentViewModel);
        return binding.getRoot();
    }

    private void setClickListeners() {
        mDetailedAppointmentViewModel.setNotificationClickListener(new IAddNotificationClickListener() {
            @Override
            public void onAddNotificationClickListener() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                boolean isActivated = sharedPreferences.getBoolean(getString(R.string.preferences_enable_notifications_switch_key), false);
                if (isActivated) {
                    mDetailedAppointmentViewModel.createNotification();
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, FragmentPreferences.newInstance())
                            .commit();
                }
            }

            @Override
            public void onRemoveNotificationClickListenrs(long appointmentId) {
                mDetailedAppointmentViewModel.updateAppointmentNotification();
                NotificationHandler.cancelReminder(String.valueOf(appointmentId));
            }
        });

        mDetailedAppointmentViewModel.setCancelAppointmentClickListener(appointmentId -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Отмена записи");
            builder.setMessage("Вы уверены что хотите отменить запись?");
            builder.setPositiveButton("ДА", (dialog, which) -> {

                mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getHomeScreenModelFactory())
                        .get(HomeScreenViewModel.class);
                mViewModel.deleteClientAppointment(appointmentId);
                ((HomeActivity) requireActivity()).deleteAppointmentFromRecycler(mPosition);
            });
            builder.setNegativeButton("Нет", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        mDetailedAppointmentViewModel.setCallPhoneClickListener(expertPhoneNumber -> {
            if ((ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + expertPhoneNumber));
                startActivity(intent);
            } else {
                this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            }
        });

        mDetailedAppointmentViewModel.setWhatsAppClickListener(expertPhoneNumber -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + expertPhoneNumber + "&text=" + "Hello"));
            requireContext().startActivity(intent);
        });

        mDetailedAppointmentViewModel.setViberClickListener(v -> {
            final String viberName = "com.viber.voip";
            if (isAppAvailable(requireContext(), viberName)) {
                Intent intent_viber = new Intent(Intent.ACTION_SEND);
                intent_viber.setPackage(viberName);
                intent_viber.setType("text/plain");
                intent_viber.putExtra(Intent.EXTRA_TEXT, "Hello");
                requireContext().startActivity(intent_viber);
            } else {
                Toast.makeText(requireActivity(), "Viber не установлен на телефоне", Toast.LENGTH_SHORT).show();
            }
        });

        mDetailedAppointmentViewModel.setTelegramClickListener(v -> {
            final String telegramName = "org.telegram.messenger";
            if (isAppAvailable(requireContext(), telegramName)) {

                Intent intent_telegramm = new Intent(Intent.ACTION_SEND);
                intent_telegramm.setPackage(telegramName);
                intent_telegramm.setType("text/plain");
                intent_telegramm.putExtra(Intent.EXTRA_TEXT, "Hello");
                requireContext().startActivity(intent_telegramm);
            } else {
                Toast.makeText(requireActivity(), "Телеграмм не установлен на телефоне", Toast.LENGTH_SHORT).show();
            }
        });
        mDetailedAppointmentViewModel.setMoreInformationClickListener(v -> {
            if (mDetailedAppointmentViewModel.getAdditionalInformationAboutExpert().getValue()) {
                mDetailedAppointmentViewModel.setAdditionalInformationAboutExpert(false);
                mDetailedAppointmentViewModel.setMoreInformationTextView(getString(R.string.fragment_detailed_information_expert_more_inf));
            } else {
                mDetailedAppointmentViewModel.setAdditionalInformationAboutExpert(true);
                mDetailedAppointmentViewModel.setMoreInformationTextView(getString(R.string.fragment_detailed_information_inf));
            }
        });
        mDetailedAppointmentViewModel.setRatingClickListener(new RatingClickListeners() {
            @Override
            public void onRatingClickListeners(float rating) {
                mDetailedAppointmentViewModel.updateAppointmentRating(mAppointmentId, rating);
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mDetailedAppointmentViewModel.getMessage().observe(getViewLifecycleOwner(), error -> {
            CustomToast.makeToast(requireActivity(), error, view);
        });
    }

    @Override
    public void onStart() {
        mDetailedAppointmentViewModel.loadDetailedInformation(mAppointmentId, mExpertId);

        super.onStart();
    }

    private static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.find_expert_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_find_expert: {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentFindExpert.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
            case R.id.menu_settings: {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, FragmentAccountSettings.newInstance())
                        .addToBackStack("active_appointments")
                        .commit();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
