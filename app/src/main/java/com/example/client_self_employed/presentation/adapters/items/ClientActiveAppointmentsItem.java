package com.example.client_self_employed.presentation.adapters.items;

import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.List;

public class ClientActiveAppointmentsItem implements RowType {

    private List<ClientAppointment> mClientAppointmentList;

    public ClientActiveAppointmentsItem(List<ClientAppointment> clientAppointmentList) {
        mClientAppointmentList = clientAppointmentList;
    }

    public List<ClientAppointment> getClientAppointmentList() {
        return mClientAppointmentList;
    }
}
