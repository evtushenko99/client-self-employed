package com.example.client_self_employed.presentation.adapters.items;

import com.example.client_self_employed.presentation.model.ClientAppointment;

import java.util.List;
import java.util.Objects;

public class ClientActiveAppointmentsItem implements RowType {

    private List<ClientAppointment> mClientAppointmentList;

    public ClientActiveAppointmentsItem(List<ClientAppointment> clientAppointmentList) {
        mClientAppointmentList = clientAppointmentList;
    }

    public List<ClientAppointment> getClientAppointmentList() {
        return mClientAppointmentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientActiveAppointmentsItem)) return false;
        ClientActiveAppointmentsItem that = (ClientActiveAppointmentsItem) o;
        return Objects.equals(getClientAppointmentList(), that.getClientAppointmentList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientAppointmentList());
    }
}
