package com.example.client_self_employed.presentation.createAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.expert.ExpertActivity;
import com.example.client_self_employed.presentation.fragments.CustomToast;
import com.google.android.material.textfield.TextInputEditText;

public class CreateExpertAccountFragment extends Fragment {
    private CreateExpertViewModel mViewModel;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mConfirmPassword;
    private TextInputEditText mLastName;
    private TextInputEditText mName;
    private TextInputEditText mSecondName;
    private TextInputEditText mAge;
    private TextInputEditText mProfession;
    private TextInputEditText mWorkExperience;
    private TextInputEditText mPhoneNumber;


    public static CreateExpertAccountFragment newInstance() {
        return new CreateExpertAccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getCreateExpertViewModelFactory())
                .get(CreateExpertViewModel.class);
        View view = inflater.inflate(R.layout.create_expert_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmail = view.findViewById(R.id.create_expert_login_field);
        mPassword = view.findViewById(R.id.create_expert_password_field);
        mConfirmPassword = view.findViewById(R.id.create_expert_confirm_password_field);
        mLastName = view.findViewById(R.id.create_expert_last_name);
        mName = view.findViewById(R.id.create_expert_name);
        mSecondName = view.findViewById(R.id.create_expert_second_name);
        mAge = view.findViewById(R.id.create_expert_age);
        mProfession = view.findViewById(R.id.create_expert_profession);
        mWorkExperience = view.findViewById(R.id.create_expert_work_experience);
        mPhoneNumber = view.findViewById(R.id.create_expert_phone_number);
        view.findViewById(R.id.create_expert_sign_in_button).setOnClickListener(v -> {
            String password = mPassword.getText().toString();
            if (password.equals(mConfirmPassword.getText().toString())) {
                Expert expert = new Expert();
                expert.setEmail(mEmail.getText().toString());
                expert.setLastName(mLastName.getText().toString());
                expert.setFirstName(mName.getText().toString());
                expert.setSecondName(mSecondName.getText().toString());
                expert.setAge(Integer.parseInt(mAge.getText().toString()));
                expert.setProfession(mProfession.getText().toString());
                expert.setWorkExperience(mWorkExperience.getText().toString());
                expert.setPhoneNumber(mPhoneNumber.getText().toString());
                mViewModel.createNewExpert(expert, password);
            } else {
                CustomToast.makeToast(requireContext(), getString(R.string.passwordError), view);
            }

        });
        mViewModel.getExpertCreated().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    Intent intent = new Intent(requireActivity(), ExpertActivity.class);
                    startActivity(intent);
                    CustomToast.makeToast(requireContext(), "Новый пользователь добавлен успешно", view);
                } else
                    CustomToast.makeToast(requireContext(), "Новый пользователь не добавлен ", view);
            }
        });
    }
}
