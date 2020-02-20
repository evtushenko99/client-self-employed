package com.example.client_self_employed.presentation.model;

import com.example.client_self_employed.presentation.adapters.items.RowType;

public class ClientSelectedExpert implements RowType {
    private long mExpertId;
    private String mExpertName;
    private String mUri;

    public ClientSelectedExpert(long expertId, String expertName, String uri) {
        mExpertId = expertId;
        mExpertName = expertName;
        mUri = uri;
    }

    public long getExpertId() {
        return mExpertId;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public String getUri() {
        return mUri;
    }
}
