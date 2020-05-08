package com.example.client_self_employed.presentation.createAccount;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.client_self_employed.R;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.create_activity_container, SelectingFragment.newInstance())
                .commit();
    }
}
