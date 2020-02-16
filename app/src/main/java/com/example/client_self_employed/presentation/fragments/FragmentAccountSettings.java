package com.example.client_self_employed.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModel;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModelFactory;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class FragmentAccountSettings extends Fragment implements View.OnClickListener {

    private static final int REQUEST_EXTERNAL_STORAGE_RESULT = 1;
    private static final int START_CAMERA_APP = 2;
    private Uri mImageUri;
    private String mImageFileLocation = "";


    private ImageView mClientPhotoImageView;
    private AppCompatEditText mClientLastNameEditText;
    private AppCompatEditText mClientNameEditText;
    private AppCompatEditText mClientSecondNamehEditText;
    private TextInputEditText mClientEmailEditText;
    private TextInputEditText mClientPhoneNumberEditText;
    private TextInputEditText mDateOfBirthEditText;
    private TextInputEditText mClietGenderEditText;
    private AccountViewModel mViewModel;
    private Client mClient;


    public static FragmentAccountSettings newInstance() {
        return new FragmentAccountSettings();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mClientPhotoImageView = view.findViewById(R.id.fragment_account_client_photo);
        mClientPhotoImageView.setOnClickListener(this);
        mClientLastNameEditText = view.findViewById(R.id.fragment_account_client_last_name);
        mClientNameEditText = view.findViewById(R.id.fragment_account_client_name);
        mClientSecondNamehEditText = view.findViewById(R.id.fragment_account_client_second_name);
        mClientEmailEditText = view.findViewById(R.id.fragment_account_client_email);
        mClientPhoneNumberEditText = view.findViewById(R.id.fragment_account_client_phone_number);
        mDateOfBirthEditText = view.findViewById(R.id.fragment_account_client_date_of_birth);
        mClietGenderEditText = view.findViewById(R.id.fragment_account_client_gender);
        mDateOfBirthEditText = view.findViewById(R.id.fragment_account_client_date_of_birth);
        mDateOfBirthEditText.setOnClickListener(this);
        view.findViewById(R.id.fragment_account_notifications).setOnClickListener(this);
        setupMVVM();
    }

    private void setupMVVM() {
        mViewModel = ViewModelProviders.of(requireActivity(), new AccountViewModelFactory(requireContext()))
                .get(AccountViewModel.class);
        mViewModel.loadInformationAboutClient();
        mViewModel.getMutableClient().observe(getViewLifecycleOwner(), client -> {
            mClient = client;
            if (mClient != null) {
                mClientLastNameEditText.setText(client.getLastName());
                mClientNameEditText.setText(client.getFirstName());
                mClientSecondNamehEditText.setText(client.getSecondName());
                mClientEmailEditText.setText(client.getEmail());
                mClientPhoneNumberEditText.setText(client.getPhoneNumber());
                mDateOfBirthEditText.setText(client.getStringDate());
                mClietGenderEditText.setText(client.getGender());
                DrawableCrossFadeFactory factory =
                        new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
                //  mClientPhotoImageView.setImageURI(uri);
                Glide
                        .with(requireContext())
                        .load(mClient.getClientPhotoUri())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .centerCrop()
                                .circleCrop()
                                .placeholder(R.mipmap.no_photo_available_or_missing)
                                .error(R.mipmap.no_photo_available_or_missing))
                        .transition(withCrossFade(factory))
                        .into(mClientPhotoImageView);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_account_notifications:
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_host_appointments_with_experts, FragmentPreferences.newInstance())
                        .commit();
                break;

            case R.id.fragment_account_client_date_of_birth:
                if (mClient != null) {
                    setDatePicker(mClient.getYearOfBirth(), mClient.getMonthOfBirth(), mClient.getDayOfBirth());
                }
                break;
            case R.id.fragment_account_client_photo:
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    callCameraApp();
                } else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(requireContext(), "Требуется разрешение на запись", Toast.LENGTH_SHORT).show();
                    }
                    this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE_RESULT);
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDatePicker(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext());
        datePickerDialog.updateDate(year, month, day);
        datePickerDialog.show();
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mViewModel.updateClientBirthday(dayOfMonth, month, year);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callCameraApp();
            } else {
                Toast.makeText(requireActivity(), "Нет разрешения на запись, фото не сохранено", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void callCameraApp() {

        Intent cameraAppIntent = new Intent();
        cameraAppIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraAppIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        File photoFile;

        photoFile = createImageFile();

        if (photoFile != null) {
            String authorities = requireActivity().getPackageName() + ".provider";

            mImageUri = FileProvider.getUriForFile(requireActivity(), authorities, photoFile);
            cameraAppIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

            this.startActivityForResult(cameraAppIntent, START_CAMERA_APP);
        }
    }

    private File createImageFile() {
        if (mClient != null) {
            String imageName = mClient.getNewImageFileName();
            File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDirectory, imageName + ".jpg");
            mImageFileLocation = imageFile.getAbsolutePath();
            return imageFile;
        } else {
            return null;
        }
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
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mImageFileLocation);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        requireContext().sendBroadcast(mediaScanIntent);
    }
}
