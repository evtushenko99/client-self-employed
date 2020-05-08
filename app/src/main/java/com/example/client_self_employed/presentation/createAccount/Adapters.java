package com.example.client_self_employed.presentation.createAccount;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputEditText;

public class Adapters {
    @BindingAdapter("addTextWatcherListener")
    public static void emailWatcher(TextInputEditText editText, MutableLiveData<String> string) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                string.postValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @BindingAdapter("errorText")
    public static void errotText(TextInputEditText editText, LiveData<String> string) {
        editText.setError(string.getValue());
    }
}
