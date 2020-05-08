package com.example.client_self_employed.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.example.client_self_employed.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private AppCompatEditText mLoginField;
    private AppCompatEditText mPasswordField;
    private AppCompatButton mSingInButton;
    private AppCompatButton mSingUpButton;


    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authorization_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mLoginField = view.findViewById(R.id.login_field);
        mPasswordField = view.findViewById(R.id.password_field);

        view.findViewById(R.id.sign_in_button).setOnClickListener(this);
        view.findViewById(R.id.sign_up_button).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                String login = mLoginField.getText().toString();
                String password = mPasswordField.getText().toString();
                singInUser(login, password);
                break;
            case R.id.sign_up_button:
                break;
        }
    }

    private void singInUser(String login, String password) {
        mAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, FragmentHomeScreen.newInstance(user.getUid()))
                                    .commit();

                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            // If sign in fails, display a message to the user.
                            Toast.makeText(requireActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

}
