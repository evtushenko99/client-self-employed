package com.example.client_self_employed.data;

import androidx.work.Data;

import com.example.client_self_employed.notification.Constants;

public class DataWrapper implements IDataWrapper {
    @Override
    public Data createInputData(String serviceName, String startTime, long appointmentId, String expertId) {
        return new Data.Builder()
                .putString(Constants.EXTRA_TITLE, serviceName)
                .putString(Constants.EXTRA_TEXT, startTime)
                .putString(Constants.EXTRA_EXPERT_ID, expertId)
                .putLong(Constants.EXTRA_APPOINTMENT_ID, appointmentId)
                .build();
    }
}
