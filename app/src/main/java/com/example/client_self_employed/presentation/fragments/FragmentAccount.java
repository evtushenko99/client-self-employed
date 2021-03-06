package com.example.client_self_employed.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.client_self_employed.R;
import com.example.client_self_employed.SelfEmployedApp;
import com.example.client_self_employed.databinding.AccountViewModelBinding;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FragmentAccount extends Fragment {
    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;
    private static final int START_CAMERA_APP = 2;
    private Uri mImageUri;
    private AccountViewModel mViewModel;


    public static FragmentAccount newInstance() {
        return new FragmentAccount();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(requireActivity(), ((SelfEmployedApp) requireContext().getApplicationContext()).getDaggerComponent().getAccountViewModelFactory())
                .get(AccountViewModel.class);
        mViewModel.setUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        AccountViewModelBinding binding = AccountViewModelBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        setClickListeners();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setClickListeners() {
        mViewModel.setOnChangeEmailClickListener(v ->
                DialogFragmentChangeExpertEmail.newInstance(mViewModel.getEmail().getValue())
                        .show(requireActivity().getSupportFragmentManager(), null));
        mViewModel.setOnChangePhoneNumberClickListener(v ->
                DialogFragmentChangeExpertPhoneNumber.newInstance(mViewModel.getPhoneNumber().getValue())
                        .show(requireActivity().getSupportFragmentManager(), null));
        mViewModel.setOnChangeFullNameClickListener(v -> DialogFragmentChangeExpertFullName.newInstance()
                .show(requireActivity().getSupportFragmentManager(), null));
        mViewModel.setOnChangeGenderClickListener(v -> DialogFragmentChangeExpertGender.newInstance(mViewModel.getGender().getValue())
                .show(requireActivity().getSupportFragmentManager(), null));
        mViewModel.setOnChangeBirthdayClickListener(this::setDatePicker);
        mViewModel.setOnChangePhotoClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(requireContext(), getResources().getString(R.string.required_write_external_permission), Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_RESULT);
            }
        });
        mViewModel.setOnNotificationClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, FragmentPreferences.newInstance())
                .commit());
        mViewModel.setOnSignOutClickListener(v -> {
            SignOutDialogFragment.newInstance().show(requireActivity().getSupportFragmentManager(), null);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getMessage().observe(getViewLifecycleOwner(), error -> {
            if (!error.equals("")) {
                CustomToast.makeToast(requireActivity(), error, view);
                mViewModel.setMessage("");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDatePicker(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
        datePickerDialog.updateDate(year, month, day);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener((view, year1, month1, dayOfMonth) -> mViewModel.updateClientBirthday(dayOfMonth, month1, year1));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(requireActivity(), getResources().getString(R.string.no_write_external_storage_permission), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callCameraApp() {
        mViewModel.createNewImageURI();
        mViewModel.getUriForFile().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                if (uri != null) {
                    mImageUri = uri;
                    startPhoto(uri);
                    //startActivityForResult(cameraAppIntent, START_CAMERA_APP);
                }
            }
        });
    }

    private void startPhoto(Uri uri) {
        Intent cameraAppIntent = new Intent();
        cameraAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraAppIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cameraAppIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        this.startActivityForResult(cameraAppIntent, START_CAMERA_APP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_CAMERA_APP && resultCode == Activity.RESULT_OK) {
            galleryAddPic();
            mViewModel.loadClientImageToStorage(mImageUri.toString());
        }
    }

    /**
     * Добавление фотографии в галерию
     */
    private void galleryAddPic() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String imageFileLocation = mViewModel.getImageFileLocation();
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(imageFileLocation);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            requireContext().sendBroadcast(mediaScanIntent);
        });

    }
}
