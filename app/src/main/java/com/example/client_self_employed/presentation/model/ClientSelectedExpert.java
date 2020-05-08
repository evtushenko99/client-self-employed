package com.example.client_self_employed.presentation.model;

import com.example.client_self_employed.presentation.adapters.items.RowType;

import java.util.Objects;

public class ClientSelectedExpert implements RowType {
    private String mExpertId;
    private String mExpertName;
    private String mUri;

    public ClientSelectedExpert(String expertId, String expertName, String uri) {
        mExpertId = expertId;
        mExpertName = expertName;
        mUri = uri;
    }

    public String getExpertId() {
        return mExpertId;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public String getUri() {
        return mUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientSelectedExpert)) return false;
        ClientSelectedExpert that = (ClientSelectedExpert) o;
        return getExpertId() == that.getExpertId() &&
                Objects.equals(getExpertName(), that.getExpertName()) &&
                Objects.equals(getUri(), that.getUri());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpertId(), getExpertName(), getUri());
    }

    @Override
    public String toString() {
        return "ClientSelectedExpert{" +
                "mExpertId=" + mExpertId +
                ", mExpertName='" + mExpertName + '\'' +
                ", mUri='" + mUri + '\'' +
                '}';
    }
}
