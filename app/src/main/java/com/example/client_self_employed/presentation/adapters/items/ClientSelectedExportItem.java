package com.example.client_self_employed.presentation.adapters.items;

import java.net.URL;

public class ClientSelectedExportItem implements RowType {
    private long mExpertId;
    private String mExpertName;
    private URL mURL;

    public ClientSelectedExportItem(long expertId) {
        mExpertId = expertId;
    }

    public ClientSelectedExportItem(long expertId, String expertName, URL URL) {
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
