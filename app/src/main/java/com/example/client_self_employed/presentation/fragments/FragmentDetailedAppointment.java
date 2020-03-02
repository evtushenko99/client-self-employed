package com.example.client_self_employed.presentation.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.client_self_employed.presentation.Arguments;
import com.example.client_self_employed.presentation.HomeActivity;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IAddNotificationClickListener;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModel;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenViewModel;

public class FragmentDetailedAppointment extends Fragment {
    private static final int REQUEST_CALL_PHONE = 0;

    private HomeScreenViewModel mHomeScreenViewModel;
    private DetailedAppointmentViewModel mViewModel;
    private long mExpertId;
    private long mAppointmentId;
    private int mPosition;

    private FragmentDetailedAppointment(long appointmentId, long expertId) {
        mExpertId = expertId;
        mAppointmentId = appointmentId;
    }

    public static FragmentDetailedAppointment newInstance(long appointmentId, long expertId, int position) {

        Bundle args = new Bundle();
        args.putLong(Arguments.APPOINTMENT_ID, appointmentId);
        args.putLong(Arguments.EXPERT_ID, expertId);
        args.putInt(Arguments.POSITION, position);
        FragmentDetailedAppointment fragment = new FragmentDetailedAppointment(appointmentId, expertId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    ((HomeActivity)  getActivity()).getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getDetailedAppointmentViewModelFactory())
                .get(DetailedAppointmentViewModel.class);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(Arguments.POSITION);
            long updateAppointment = bundle.getLong(Constants.UPDATE_APPOINTMENT, -1);
            if (updateAppointment != -1) {
                mViewModel.setUpdateButtonText(true);
            }
        }
        setClickListeners();
        FragmentDetailedAppointmentBinding binding = FragmentDetailedAppointmentBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }

    private void setClickListeners() {
        Resources resources = getResources();
        mViewModel.setNotificationClickListener(new IAddNotificationClickListener() {
            @Override
            public void onAddNotificationClickListener() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                boolean isActivated = sharedPreferences.getBoolean(getString(R.string.preferences_enable_notifications_switch_key), false);
                if (isActivated) {
                    mViewModel.createNotification();
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.fragment_container, FragmentPreferences.newInstance())
                            .commit();
                }
            }

            @Override
            public void onRemoveNotificationClickListenrs(long appointmentId) {
                mViewModel.updateAppointmentNotification();
                NotificationHandler.cancelReminder(String.valueOf(appointmentId));
            }
        });

        mViewModel.setCancelAppointmentClickListener(appointmentId -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(resources.getString(R.string.cancel_active_record));
            builder.setMessage(resources.getString(R.string.are_you_sure_you_want_to_cancel_recording));
            builder.setPositiveButton(resources.getString(R.string.positive_button), (dialog, which) -> {
                mHomeScreenViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getHomeScreenModelFactory())
                        .get(HomeScreenViewModel.class);
                mHomeScreenViewModel.deleteClientAppointment(appointmentId);
                ((HomeActivity) requireActivity()).deleteAppointmentFromRecycler(mPosition);
            });
            builder.setNegativeButton(resources.getString(R.string.negative_button), null);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        mViewModel.setCallPhoneClickListener(expertPhoneNumber -> {
            if ((ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + expertPhoneNumber));
                startActivity(intent);
            } else {
                this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            }
        });

        mViewModel.setWhatsAppClickListener(expertPhoneNumber -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + expertPhoneNumber + "&text=" + "Hello"));
            requireContext().startActivity(intent);
        });

        mViewModel.setViberClickListener(v -> {
            final String viberName = resources.getString(R.string.viber_name);
            if (isAppAvailable(requireContext(), viberName)) {
                Intent intent_viber = new Intent(Intent.ACTION_SEND);
                intent_viber.setPackage(viberName);
                intent_viber.setType("text/plain");
                intent_viber.putExtra(Intent.EXTRA_TEXT, "Hello");
                requireContext().startActivity(intent_viber);
            } else {
                Toast.makeText(requireActivity(), resources.getString(R.string.viber_not_available_on_this_phone), Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.setTelegramClickListener(v -> {
            final String telegramName = resources.getString(R.string.telegram_name);
            if (isAppAvailable(requireContext(), telegramName)) {

                Intent intent_telegramm = new Intent(Intent.ACTION_SEND);
                intent_telegramm.setPackage(telegramName);
                intent_telegramm.setType("text/plain");
                intent_telegramm.putExtra(Intent.EXTRA_TEXT, "Hello");
                requireContext().startActivity(intent_telegramm);
            } else {
                Toast.makeText(requireActivity(), resources.getString(R.string.telegram_not_available_on_this_phone), Toast.LENGTH_SHORT).show();
            }
        });
        mViewModel.setMoreInformationClickListener(v -> {
            if (mViewModel.getAdditionalInformationAboutExpert().getValue()) {
                mViewModel.setAdditionalInformationAboutExpert(false);
                mViewModel.setMoreInformationTextView(getString(R.string.fragment_detailed_information_expert_more_inf));
            } else {
                mViewModel.setAdditionalInformationAboutExpert(true);
                mViewModel.setMoreInformationTextView(getString(R.string.fragment_detailed_information_inf));
            }
        });
        mViewModel.setRatingClickListener(rating -> mViewModel.updateAppointmentRating(mAppointmentId, rating));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getMessage().observe(getViewLifecycleOwner(), error ->
                CustomToast.makeToast(requireActivity(), error, view));
    }

    @Override
    public void onStart() {
        mViewModel.loadDetailedInformation(mAppointmentId, mExpertId);
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
}
