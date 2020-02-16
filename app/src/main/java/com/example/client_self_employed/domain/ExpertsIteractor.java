package com.example.client_self_employed.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.client_self_employed.data.IExpertsRepository;
import com.example.client_self_employed.domain.model.Expert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExpertsIteractor {
    private final IExpertsRepository mRepositoryExperts;

    public ExpertsIteractor(IExpertsRepository repositoryExperts) {
        mRepositoryExperts = repositoryExperts;
    }

    public void loadAllExperts(IExpertCallBack callBack) {
        mRepositoryExperts.loadAllExperts(callBack);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Expert> findExpert(List<Expert> expertList, String query) {
        List<Expert> experts;
        Stream stream = expertList.stream();
        experts = (List<Expert>) stream.filter(expert ->
                ((Expert) expert).getFirstName().toLowerCase().contains(query.toLowerCase())
                        || ((Expert) expert).getLastName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());

        return experts;
    }
}
