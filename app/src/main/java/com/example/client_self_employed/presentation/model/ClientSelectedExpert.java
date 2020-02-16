package com.example.client_self_employed.presentation.model;

import android.net.Uri;

import com.example.client_self_employed.presentation.adapters.items.RowType;

public class ClientSelectedExpert implements RowType {
    private long mExpertId;
    private String mExpertName;
    private Uri mUri;

    public ClientSelectedExpert(long expertId, String expertName, Uri uri) {
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

    public Uri getUri() {
        return mUri;
    }
}
