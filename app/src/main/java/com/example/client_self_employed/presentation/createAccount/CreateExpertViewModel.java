package com.example.client_self_employed.presentation.createAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.domain.CreateExpertInteractor;
import com.example.client_self_employed.domain.ICreateExpertCallback;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.google.firebase.database.annotations.NotNull;

import java.util.concurrent.Executor;

public class CreateExpertViewModel extends ViewModel {
    private final CreateExpertInteractor mInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private Expert mExpert;


    private MutableLiveData<Boolean> mExpertCreated = new MutableLiveData<>();
    private ICreateExpertCallback mCallback = (b, uid) -> {
        if (b) {
            mExpertCreated.postValue(true);
        }
    };

    public CreateExpertViewModel(CreateExpertInteractor createExpertInteractor, Executor executor, ResourceWrapper resourceWrapper) {
        mInteractor = createExpertInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }

    public void createNewExpert(@NotNull Expert expert, String password) {
        mExpert = expert;
        mExecutor.execute(() -> mInteractor.createExpert(mExpert, password, mCallback));
    }

    public LiveData<Boolean> getExpertCreated() {
        return mExpertCreated;
    }

}
