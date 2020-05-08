package com.example.client_self_employed.data;

import androidx.work.Data;

public interface IDataWrapper {
    Data createInputData(String serviceName, String startTime, long appointmentId, String expertId);
}
