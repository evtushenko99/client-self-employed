package com.example.client_self_employed.presentation.model;

import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.net.URL;

public class ClientSelectedExpert implements RowType {
    private long mExpertId;
    private String mExpertName;
    private URL mURL;

    public ClientSelectedExpert(long expertId, String expertName, URL URL) {
        mExpertId = expertId;
        mExpertName = expertName;
        mURL = URL;
    }

    public long getExpertId() {
        return mExpertId;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public URL getURL() {
        return mURL;
    }
}
