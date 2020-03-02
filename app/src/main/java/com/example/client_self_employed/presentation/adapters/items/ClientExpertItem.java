package com.example.client_self_employed.presentation.adapters.items;

import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.List;
import java.util.Objects;

public class ClientExpertItem implements RowType {
    private List<ClientSelectedExpert> mClientSelectedExperts;

    public ClientExpertItem(List<ClientSelectedExpert> clientSelectedExpertItems) {
        mClientSelectedExperts = clientSelectedExpertItems;
    }
    public List<ClientSelectedExpert> getClientSelectedExperts() {
        return mClientSelectedExperts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientExpertItem)) return false;
        ClientExpertItem item = (ClientExpertItem) o;
        return Objects.equals(getClientSelectedExperts(), item.getClientSelectedExperts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientSelectedExperts());
    }
}
