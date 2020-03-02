package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.IExpertsCallBack;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.IResourceWrapper;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class FindExpertViewModel extends ViewModel {
    private final ExpertInteractor mExpertInteractor;
    private final Executor mExecutor;
    private final IResourceWrapper mResourceWrapper;
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> mErrors = new MutableLiveData<>();
    private List<Expert> mExperts = new ArrayList<>();
    private MutableLiveData<String> mSearchQuery = new MutableLiveData<>();
    private MutableLiveData<List<Expert>> mLiveData = new MutableLiveData<>();
    private IExpertsCallBack mExpertCallBack = new IExpertsCallBack() {
        @Override
        public void expertsIsLoaded(@Nullable List<Expert> expertList) {
            mExperts = expertList;
            mLiveData.postValue(mExperts);
            mIsLoading.postValue(false);
        }

        @Override
        public void errorLoadingExperts(String errorLoadingExperts) {
            mErrors.postValue(errorLoadingExperts);
        }
    };

    public FindExpertViewModel(ExpertInteractor appointmentsIteractor, Executor executor, ResourceWrapper resourceWrapper) {
        mExecutor = executor;
        mExpertInteractor = appointmentsIteractor;
        mResourceWrapper = resourceWrapper;
    }

    public void loadAllExperts() {
        mIsLoading.setValue(true);
        mExecutor.execute(() -> {
            mExpertInteractor.loadAllExperts(mExpertCallBack);
        });
    }

    public void setSearchQuery(String searchQuery) {
        if (!searchQuery.equals("")) {
            mExecutor.execute(() -> {
                List<Expert> newExpets = mExpertInteractor.findExpert(mExperts, searchQuery);
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

    @VisibleForTesting
    public IExpertsCallBack getExpertCallBack() {
        return mExpertCallBack;
    }


}
