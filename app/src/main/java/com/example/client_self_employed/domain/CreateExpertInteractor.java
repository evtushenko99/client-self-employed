package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.data.IFileWrapper;
import com.example.client_self_employed.domain.model.Expert;

public class CreateExpertInteractor {
    private final IExpertRepository mExpertRepository;
    private final IFileWrapper mFileWrapper;

    public CreateExpertInteractor(IExpertRepository expertRepository, IFileWrapper fileWrapper) {
        mExpertRepository = expertRepository;
        mFileWrapper = fileWrapper;
    }

    public void createExpert(Expert expert, ICreateExpertCallback createExpertCallback) {
        mExpertRepository.createExpert(expert, createExpertCallback);
    }


}
