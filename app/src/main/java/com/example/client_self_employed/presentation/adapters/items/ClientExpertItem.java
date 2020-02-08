package com.example.client_self_employed.presentation.adapters.items;

import java.util.List;

public class ClientExpertItem implements RowType {
    private String mTitle;
    private List<ClientSelectedExportItem> mClientSelectedExportItems;

    public ClientExpertItem(String title, List<ClientSelectedExportItem> clientSelectedExportItems) {
        mTitle = title;
        mClientSelectedExportItems = clientSelectedExportItems;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<ClientSelectedExportItem> getClientSelectedExportItems() {
        return mClientSelectedExportItems;
    }

}
