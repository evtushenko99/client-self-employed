package com.example.client_self_employed.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.client_self_employed.R;
import com.example.client_self_employed.data.RepositoryClient;
import com.example.client_self_employed.data.RepositoryExpert;
import com.example.client_self_employed.domain.IAllUsersCallback;
import com.example.client_self_employed.notification.Constants;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.createAccount.CreateActivity;
import com.example.client_self_employed.presentation.expert.ExpertActivity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText mLoginField;
    private EditText mPasswordField;
    private RepositoryExpert mRepositoryExpert;
    private RepositoryClient mRepositoryClient;
    private Executor mExecutor = Executors.newFixedThreadPool(1);

    private IAllUsersCallback mIAllUsersCallback = new IAllUsersCallback() {
        @Override
        public void onClientCallBack(Boolean b) {
            if (b) {
                startClient();
            }
        }

        @Override
        public void onExpertCallBack(Boolean b) {
            if (b) {
                startExpert();
            }
        }
    };

    private void startExpert() {
        if (mUser != null) {
            Intent intent = new Intent(this, ExpertActivity.class);
            intent.putExtra(Constants.EXPERT_ID, mUser.getUid());
            startActivity(intent);
        }
    }

    private void startClient() {
        if (mUser != null) {
            Intent intent = new Intent(this, HomeClientActivity.class);
            intent.putExtra(Constants.CLIENT_UID, mUser.getUid());
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        mLoginField = findViewById(R.id.login_field);
        mPasswordField = findViewById(R.id.password_field);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // updateUI(currentUser);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);

        mRepositoryClient = new RepositoryClient(new ResourceWrapper(this.getResources()));
        mRepositoryExpert = new RepositoryExpert(new ResourceWrapper(this.getResources()));
        if (currentUser != null)
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mRepositoryExpert.findExpert(currentUser.getUid(), mIAllUsersCallback);
                    mRepositoryClient.findClient(currentUser.getUid(), mIAllUsersCallback);
                }
            });
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
                            mUser = mAuth.getCurrentUser();

                            mExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mRepositoryExpert.findExpert(mUser.getUid(), mIAllUsersCallback);
                                    mRepositoryClient.findClient(mUser.getUid(), mIAllUsersCallback);
                                }
                            });
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
}
