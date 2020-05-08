package com.example.client_self_employed.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.client_self_employed.R;
import com.example.client_self_employed.notification.Constants;
import com.example.client_self_employed.presentation.createAccount.CreateActivity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private AppCompatEditText mLoginField;
    private AppCompatEditText mPasswordField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        mLoginField = findViewById(R.id.login_field);
        mPasswordField = findViewById(R.id.password_field);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                String login = mLoginField.getText().toString();
                String password = mPasswordField.getText().toString();
                if (!(login.equals("")) && !(password.equals("")))
                    singInUser(login, password);
                break;
            case R.id.sign_up_button:
                startActivity(new Intent(this, CreateActivity.class));
                break;
        }
    }

    private void singInUser(String login, String password) {
        mAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Authentication onFailed.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCanceledListener(this, new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(getBaseContext(), "Authentication onCanceled.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constants.CLIENT_UID, user.getUid());
            startActivity(intent);
        }

    }
}
