package com.example.client_self_employed.presentation.adapters.items;

import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.List;

public class ClientExpertItem implements RowType {
    private String mTitle;
    private List<ClientSelectedExpert> mClientSelectedExperts;

    public ClientExpertItem(List<ClientSelectedExpert> clientSelectedExpertItems) {

        mClientSelectedExperts = clientSelectedExpertItems;
    }


    public List<ClientSelectedExpert> getClientSelectedExperts() {
        return mClientSelectedExperts;
    }

}
