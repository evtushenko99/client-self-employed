package com.example.client_self_employed.domain;

public class ExpertsIteractor {
    private final IExpertsRepository mRepositoryExperts;

    public ExpertsIteractor(IExpertsRepository repositoryExperts) {
        mRepositoryExperts = repositoryExperts;
    }

    public void loadAllExperts(IExpertCallBack callBack) {
        mRepositoryExperts.loadAllExperts(callBack);
    }
}
