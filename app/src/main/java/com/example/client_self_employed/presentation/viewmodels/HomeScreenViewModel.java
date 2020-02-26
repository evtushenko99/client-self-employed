package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.domain.AppointmentsInteractor;
import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.domain.IAppointmentsCallback;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertsCallBack;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ModelsConverter;
import com.example.client_self_employed.presentation.adapters.AdapterHomeScreen;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class HomeScreenViewModel extends ViewModel {
    private final AppointmentsInteractor mAppointmentsInteractor;
    private final ExpertsIteractor mExpertsInteractor;
    private final Executor mExecutor;
    private final ObservableField<Boolean> mIsBestExpertLoading = new ObservableField<>(false);
    private final ObservableField<Boolean> mIsActiveAppointmentLoading = new ObservableField<>(false);
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();
    private ObservableField<List<RowType>> mLiveData = new ObservableField<>();
    private List<RowType> mRowTypes = new ArrayList<>();

    @VisibleForTesting
    IExpertsCallBack getExpertsCallBack() {
        return mExpertsCallBack;
    }

    private IExpertsCallBack mExpertsCallBack = new IExpertsCallBack() {
        @Override
        public void expertsIsLoaded(@NonNull List<Expert> expertList) {

            List<ClientSelectedExpert> list = new ArrayList<>();
            if (expertList.size() != 0) {
                for (Expert expert : expertList) {
                    list.add(new ClientSelectedExpert(expert.getId(), expert.getAbbreviatedFullName(), expert.getExpertPhotoUri()));
                }
                ClientExpertItem item = new ClientExpertItem(list);
                if (mRowTypes.size() == 0) {
                    mRowTypes.add(0, item);
                    mLiveData.set(mRowTypes);
                }

                loadActiveAppointments();
            }
            mIsBestExpertLoading.set(false);
        }

        @Override
        public void errorLoadingExperts(String errorLoadingExperts) {
            mErrors.postValue(errorLoadingExperts);
            mIsBestExpertLoading.set(false);
        }
    };

    private IClientAppointmentCallback mClientAppointmentCallback = new IClientAppointmentCallback() {
        @Override
        public void clientsAppointmentsIsLoaded(List<Appointment> appointmentList, List<Expert> expertList) {
            mRowTypes = new ArrayList<>(mRowTypes.subList(0, 1));

            if (appointmentList.size() != 0 && expertList.size() != 0) {
                List<ClientAppointment> activeAppointments = ModelsConverter.convertAppointmentToRowType(appointmentList, expertList);
                if (appointmentList != null) {

                    ClientActiveAppointmentsItem clientActiveAppointmentsItem = new ClientActiveAppointmentsItem(activeAppointments);
                    if (mRowTypes.size() == 1) {
                        mRowTypes.add(clientActiveAppointmentsItem);
                        mLiveData.set(mRowTypes);
                    }
                }
            } else {
                mRowTypes.add(new ClientNoAppointmentItem());
                mLiveData.set(mRowTypes);
            }
            mIsActiveAppointmentLoading.set(false);

        }

        @Override
        public void clientAppointmentIsDeleted(Boolean isDeleted) {
            if (isDeleted) {
                loadActiveAppointments();
            }
        }

        @Override
        public void errorOnLoadingClientExperts(String errorOnLoadingClientExperts) {
            mErrors.postValue(errorOnLoadingClientExperts);
            mIsActiveAppointmentLoading.set(false);
        }

        @Override
        public void errorDeletingAppointment(String errorDeletingAppointment) {
            mErrors.postValue(errorDeletingAppointment);
        }


    };
    private IAppointmentsCallback mAppointmentsCallback = new IAppointmentsCallback() {
        @Override
        public void onAppointmentCallback(List<Appointment> appointments, List<Long> expertsId) {
            mExpertsInteractor.loadExperNameForActiveAppointment(appointments, expertsId, mClientAppointmentCallback);
        }

        @Override
        public void onErrorLoadingActiveAppointments(String errorLoadingActiveAppointments) {
            mErrors.postValue(errorLoadingActiveAppointments);
        }

    };

    HomeScreenViewModel(
            @NonNull AppointmentsInteractor iteractor,
            @NonNull ExpertsIteractor expertsInteractor, @NonNull Executor executor) {
        mAppointmentsInteractor = iteractor;
        mExpertsInteractor = expertsInteractor;
        mExecutor = executor;
    }

    @BindingAdapter("data")
    public static void setRecycler(RecyclerView recycler, List<RowType> data) {
        if (data != null) {
            ((AdapterHomeScreen) recycler.getAdapter()).setRowTypes(data);
        }
    }


    public void loadClientExperts() {
        mIsBestExpertLoading.set(true);
        mExecutor.execute(() -> mExpertsInteractor.loadAllExperts(mExpertsCallBack));
    }

    public void loadActiveAppointments() {
        mIsActiveAppointmentLoading.set(true);
        mExecutor.execute(() -> mAppointmentsInteractor.loadClientsAppointments(2, mAppointmentsCallback));
    }

    public void deleteClientAppointment(long appointmentId) {
        mExecutor.execute(() -> mAppointmentsInteractor.deleteClientAppointment(appointmentId, mClientAppointmentCallback));
    }

    public ObservableField<Boolean> getIsBestExpertLoading() {
        return mIsBestExpertLoading;
    }

    public ObservableField<Boolean> getIsActiveAppointmentLoading() {
        return mIsActiveAppointmentLoading;
    }

    public ObservableField<List<RowType>> getLiveData() {
        return mLiveData;
    }
    public LiveData<String> getErrors() {
        return mErrors;
    }


}

