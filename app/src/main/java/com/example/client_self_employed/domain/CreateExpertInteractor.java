package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.domain.model.Expert;

public class CreateExpertInteractor {
    private final IExpertRepository mExpertRepository;

    public CreateExpertInteractor(IExpertRepository expertRepository) {
        mExpertRepository = expertRepository;

    }

    public void createExpert(Expert expert, String password, ICreateExpertCallback createExpertCallback) {
        mExpertRepository.createExpert(expert, password, createExpertCallback);
    }


}
