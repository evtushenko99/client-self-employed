package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.domain.AppointmentsInteractor;
import com.example.client_self_employed.domain.ExpertsIteractor;
import com.example.client_self_employed.domain.IAppointmentsCallback;
import com.example.client_self_employed.domain.IClientAppointmentCallback;
import com.example.client_self_employed.domain.IExpertCallBack;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.adapters.AdapterClientsAppointments;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;
import com.example.client_self_employed.presentation.model.ModelsConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class AppointmentsViewModel extends ViewModel {
    private final AppointmentsInteractor mAppointmentsInteractor;
    private final ExpertsIteractor mExpertsInteractor;
    private final Executor mExecutor;
    private final ObservableField<Boolean> mIsBestExpertLoading = new ObservableField<>();
    private final ObservableField<Boolean> mIsActiveAppointmentLoading = new ObservableField<>();
    private final MutableLiveData<String> mErrors = new MutableLiveData<>();
    private ObservableField<List<RowType>> mLiveData = new ObservableField<>();
    private List<RowType> mRowTypes = new ArrayList<>();

    @VisibleForTesting
    IExpertCallBack getExpertsCallBack() {
        return mExpertsCallBack;
    }

    private IExpertCallBack mExpertsCallBack = new IExpertCallBack() {
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
                mIsBestExpertLoading.set(false);
                loadActiveAppointments();
            }
        }
    };

    private IClientAppointmentCallback mClientAppointmentCallback = new IClientAppointmentCallback() {
        @Override
        public void clientsAppointmentsIsLoaded(List<Appointment> appointmentList, List<Expert> expertList) {
            mRowTypes = new ArrayList<>(mRowTypes.subList(0, 1));
            mIsActiveAppointmentLoading.set(false);
            if (appointmentList.size() != 0 && expertList.size() != 0) {
                List<ClientAppointment> activeAppointments = ModelsConverter.convertAppointmentToRowType(appointmentList, expertList);
                if (activeAppointments != null) {
                    ClientActiveAppointmentsItem clientActiveAppointmentsItem = new ClientActiveAppointmentsItem(activeAppointments);
                    if (mRowTypes.size() == 1) {
                        mRowTypes.add(clientActiveAppointmentsItem);
                        mLiveData.set(mRowTypes);
                    } else if (mRowTypes.get(1) != clientActiveAppointmentsItem) {
                        mRowTypes.add(clientActiveAppointmentsItem);
                        mLiveData.set(mRowTypes);
                    }
                }
            } else {
                mRowTypes.add(new ClientNoAppointmentItem());
                mLiveData.set(mRowTypes);
            }


        }

        @Override
        public void clientAppointmentIsDeleted(Boolean isDeleted) {
            if (isDeleted) {
                loadActiveAppointments();
            }
        }
    };
    private IAppointmentsCallback mAppointmentsCallback = new IAppointmentsCallback() {
        @Override
        public void onAppointmentCallback(List<Appointment> appointments, List<Long> expertsId) {
            mExpertsInteractor.loadExperNameForActiveAppointment(appointments, expertsId, mClientAppointmentCallback);
        }
    };

    AppointmentsViewModel(
            @NonNull AppointmentsInteractor iteractor,
            @NonNull ExpertsIteractor expertsInteractor, @NonNull Executor executor) {
        mAppointmentsInteractor = iteractor;
        mExpertsInteractor = expertsInteractor;
        mExecutor = executor;
    }

    @BindingAdapter("data")
    public static void setRecycler(RecyclerView recycler, List<RowType> data) {
        if (data != null) {
            ((AdapterClientsAppointments) recycler.getAdapter()).setRowTypes(data);
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

    public ObservableField<List<RowType>> getLiveData() {
        return mLiveData;
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


}

