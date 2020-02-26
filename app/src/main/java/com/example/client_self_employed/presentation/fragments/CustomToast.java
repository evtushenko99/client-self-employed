package com.example.client_self_employed.presentation.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.client_self_employed.R;

public class CustomToast {
    public static void makeToast(Context context, String message, View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast, view.findViewById(R.id.custom_toast_layout));
        TextView tV = layout.findViewById(R.id.toast);
        tV.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
