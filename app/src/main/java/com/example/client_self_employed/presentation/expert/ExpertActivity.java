package com.example.client_self_employed.presentation.expert;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.client_self_employed.R;

public class ExpertActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_expert);

        mToolbar = findViewById(R.id.toolbar_expert);
        mToolbar.setTitle(R.string.title_home);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_expert_container, HomeExpertFragment.newInstance())
                .commit();
    }
}
