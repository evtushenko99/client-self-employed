package com.example.client_self_employed.presentation.viewmodels;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class FindExpertViewModel extends ViewModel {
    private final ExpertsIteractor mExpertsIteractor;
    private final Executor mExecutor;
    private final IResourceWrapper mResourceWrapper;
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();
    private List<Expert> mExperts = new ArrayList<>();
    private MutableLiveData<String> mSearchQuery = new MutableLiveData<>();
    private MutableLiveData<List<Expert>> mLiveData = new MutableLiveData<>();
    private IExpertCallBack mExpertCallBack = new IExpertCallBack() {
        @Override
        public void expertsIsLoaded(@Nullable List<Expert> expertList) {
            mExperts = expertList;
            mLiveData.postValue(mExperts);
            mIsLoading.postValue(false);
        }
    };

    public FindExpertViewModel(ExpertsIteractor appointmentsIteractor, Executor executor, ResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mExpertsIteractor = appointmentsIteractor;
        mResourceWrapper = resourceWrapper;
        mIsLoading.setValue(false);
    }

    public void loadAllEcperts() {
        mIsLoading.setValue(true);

        mExecutor.execute(() -> {
            mExpertsIteractor.loadAllExperts(mExpertCallBack);
        });
    }

    public MutableLiveData<String> getSearchQuery() {
        return mSearchQuery;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSearchQuery(String searchQuery) {
        if (!searchQuery.equals("")) {
            mExecutor.execute(() -> {
                List<Expert> newExpets = mExpertsIteractor.findExpert(mExperts, searchQuery);
                mLiveData.postValue(newExpets);

            });
        } else {
            mLiveData.postValue(mExperts);
        }
    }


    public LiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public LiveData<String> getErrors() {
        return mErrors;
    }

    public LiveData<List<Expert>> getLiveData() {
        return mLiveData;
    }


}
