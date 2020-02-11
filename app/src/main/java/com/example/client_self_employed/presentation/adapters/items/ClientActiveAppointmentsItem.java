package com.example.client_self_employed.presentation.adapters.items;

import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.List;

public class ClientActiveAppointmentsItem implements RowType {

    private List<ClientAppointment> mClientSelectedExperts;

    public ClientActiveAppointmentsItem(List<ClientAppointment> clientSelectedExperts) {
        mClientSelectedExperts = clientSelectedExperts;
    }

    public List<ClientAppointment> getClientSelectedExperts() {
        return mClientSelectedExperts;
    }
}
