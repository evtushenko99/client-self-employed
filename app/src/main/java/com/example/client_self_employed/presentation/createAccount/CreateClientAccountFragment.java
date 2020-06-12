package com.example.client_self_employed.presentation.createAccount;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.CreateClientViewModelBinding;
import com.example.client_self_employed.notification.Constants;
import com.example.client_self_employed.presentation.HomeClientActivity;
import com.example.client_self_employed.presentation.fragments.CustomToast;

public class CreateClientAccountFragment extends Fragment {

    private CreateClientViewModel mViewModel;

    public static CreateClientAccountFragment newInstance() {
        return new CreateClientAccountFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getCreateClientViewModelFactory())
                .get(CreateClientViewModel.class);
        CreateClientViewModelBinding binding = CreateClientViewModelBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        setClickListeners(binding.getRoot());
        // View view = inflater.inflate(R.layout.create_client_account, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setClickListeners(View root) {
        mViewModel.getClientUid().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Intent intent = new Intent(requireActivity(), HomeClientActivity.class);
                    intent.putExtra(Constants.CLIENT_UID, s);
                    startActivity(intent);
                }
            }
        });
        mViewModel.getClientCreated().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    CustomToast.makeToast(requireContext(), "Новый пользователь добавлен успешно", root);
                } else
                    CustomToast.makeToast(requireContext(), "Новый пользователь не добавлен ", root);
            }
        });
        mViewModel.setOnCreateClient(v -> {
            mViewModel.onConfirm();
        });
        mViewModel.setOnSelectGenderClickListener(v -> SelectNewGenderDialogFragment.newInstance()
                .show(requireActivity().getSupportFragmentManager(), null));
        mViewModel.setOnSelectBirthDayClickListener(this::setDatePicker);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDatePicker(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
        datePickerDialog.updateDate(year, month, day);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener((view, year1, month1, dayOfMonth) -> mViewModel.setBirthDay(dayOfMonth, month1, year1));
    }

}
