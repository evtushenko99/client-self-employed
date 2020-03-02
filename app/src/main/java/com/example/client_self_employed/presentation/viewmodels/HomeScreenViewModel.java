package com.example.client_self_employed.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
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
import com.example.client_self_employed.presentation.clicklisteners.ActiveAppointmentClickListener;
import com.example.client_self_employed.presentation.clicklisteners.FindExpertButtonClickListener;
import com.example.client_self_employed.presentation.clicklisteners.NewRecordToBestExpertButtonItemClickListener;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class HomeScreenViewModel extends ViewModel {
    private final AppointmentInteractor mAppointmentInteractor;
    private final ExpertInteractor mExpertsInteractor;
    private final FilterActiveAppointmentsInteractor mFilterInteractor;
    private final Executor mExecutor;
    private final ModelsConverter mModelsConverter;
    private final MutableLiveData<Boolean> mIsBestExpertLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> mIsActiveAppointmentLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> mMessage = new MutableLiveData<>();
    private MutableLiveData<List<RowType>> mLiveData = new MutableLiveData<>();
    private List<RowType> mRowTypes = new ArrayList<>();
    //Переменная, которая говорит нам, сколько ответов пришло от БД при загрузки фотографий для каждого эксперта
    //Только если количество ответов от БД равно количеству экспертов в массиве (все фотографии загружены)
    //можно продолжать загрузку
    private int count = 0;

    private ActiveAppointmentClickListener mActiveAppointmentsClickListener;
    private NewRecordToBestExpertButtonItemClickListener mNewRecordToBestExpertButtonItemClickListener;
    private FindExpertButtonClickListener mFindExpertButtonClickListener;

    private IExpertsCallBack mExpertsCallBack = new IExpertsCallBack() {
        @Override
        public void expertsIsLoaded(@NonNull List<Expert> expertList) {
            count++;
            if (count == expertList.size()) {
                List<ClientSelectedExpert> list = new ArrayList<>();
                if (expertList.size() != 0) {
                    for (Expert expert : expertList) {
                        list.add(new ClientSelectedExpert(expert.getId(), expert.getAbbreviatedFullName(), expert.getExpertPhotoUri()));
                    }
                    ClientExpertItem item = new ClientExpertItem(list);
                    if (mRowTypes.size() == 0) {
                        mRowTypes.add(0, item);
                        mLiveData.setValue(mRowTypes);
                    }
                    loadActiveAppointments();
                }
                mIsBestExpertLoading.setValue(false);
                count = 0;
            }
        }

        @Override
        public void messageLoadingExperts(String messageLoadingExperts) {
            mMessage.postValue(messageLoadingExperts);
            mIsBestExpertLoading.setValue(false);
        }
    };

    private IClientAppointmentCallback mClientAppointmentCallback = new IClientAppointmentCallback() {
        @Override
        public void clientsAppointmentsIsLoaded(List<Appointment> appointmentList, List<Expert> expertList) {
            mRowTypes = new ArrayList<>(mRowTypes.subList(0, 1));

            if (appointmentList.size() != 0 && expertList.size() != 0) {
                List<ClientAppointment> activeAppointments = mModelsConverter.convertAppointmentToRowType(appointmentList, expertList);
                if (appointmentList != null) {
                    ClientActiveAppointmentsItem clientActiveAppointmentsItem = new ClientActiveAppointmentsItem(activeAppointments);
                    if (mRowTypes.size() == 1) {
                        mRowTypes.add(clientActiveAppointmentsItem);
                        mLiveData.setValue(mRowTypes);
                    }
                }
            } else {
                mRowTypes.add(new ClientNoAppointmentItem());
                mLiveData.setValue(mRowTypes);
            }
            mIsActiveAppointmentLoading.setValue(false);
        }

        @Override
        public void clientAppointmentIsDeleted(Boolean isDeleted) {
            if (isDeleted) {
                loadActiveAppointments();
            }
        }

        @Override
        public void message(String message) {
            mMessage.postValue(message);
            mIsActiveAppointmentLoading.setValue(false);
        }
    };

    private IAppointmentsCallback mAppointmentsCallback = new IAppointmentsCallback() {
        @Override
        public void onAppointmentCallback(List<Appointment> appointments, List<Long> expertsId) {
            mExpertsInteractor.loadExperNameForActiveAppointment(appointments, expertsId, mClientAppointmentCallback);
        }

        @Override
        public void onErrorLoadingActiveAppointments(String errorLoadingActiveAppointments) {
            mMessage.postValue(errorLoadingActiveAppointments);
        }

    };

    HomeScreenViewModel(
            @NonNull AppointmentInteractor iteractor,
            @NonNull ExpertInteractor expertsInteractor,
            @NonNull FilterActiveAppointmentsInteractor filterActiveAppointmentsInteractor,
            @NonNull Executor executor) {
        mAppointmentInteractor = iteractor;
        mExpertsInteractor = expertsInteractor;
        mFilterInteractor = filterActiveAppointmentsInteractor;
        mExecutor = executor;
        mModelsConverter = new ModelsConverter(mFilterInteractor);

    }

    @BindingAdapter({"data", "activeAppointmentClickListener", "newRecordClickListener", "findExpertButtonClickListener"})
    public static void setRecycler(RecyclerView recycler, List<RowType> data, ActiveAppointmentClickListener activeAppointmentClickListener,
                                   NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener,
                                   FindExpertButtonClickListener findExpertButtonClickListener) {
        if (data != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recycler.getContext(), RecyclerView.VERTICAL, false);
            recycler.setLayoutManager(linearLayoutManager);
            recycler.setAdapter(new AdapterHomeScreen(data, activeAppointmentClickListener, newRecordToBestExpertButtonItemClickListener, findExpertButtonClickListener));
        }
    }

    public void loadClientExperts() {
        mIsBestExpertLoading.setValue(true);
        mExecutor.execute(() -> mExpertsInteractor.loadAllExperts(mExpertsCallBack));
    }

    public void loadActiveAppointments() {
        mIsActiveAppointmentLoading.setValue(true);
        mExecutor.execute(() -> mAppointmentInteractor.loadClientsAppointments(2, mAppointmentsCallback));
    }

    public void deleteClientAppointment(long appointmentId) {
        mExecutor.execute(() -> mAppointmentInteractor.deleteClientAppointment(appointmentId, mClientAppointmentCallback));
    }


    public void setActiveAppointmentsClickListener(ActiveAppointmentClickListener activeAppointmentsClickListener) {
        mActiveAppointmentsClickListener = activeAppointmentsClickListener;
    }


    public void setNewRecordToBestExpertButtonItemClickListener(NewRecordToBestExpertButtonItemClickListener newRecordToBestExpertButtonItemClickListener) {
        mNewRecordToBestExpertButtonItemClickListener = newRecordToBestExpertButtonItemClickListener;
    }


    public void setFindExpertButtonClickListener(FindExpertButtonClickListener findExpertButtonClickListener) {
        mFindExpertButtonClickListener = findExpertButtonClickListener;
    }


    public LiveData<Boolean> getIsBestExpertLoading() {
        return mIsBestExpertLoading;
    }

    public LiveData<Boolean> getIsActiveAppointmentLoading() {
        return mIsActiveAppointmentLoading;
    }

    public LiveData<List<RowType>> getLiveData() {
        return mLiveData;
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    public FilterActiveAppointmentsInteractor getFilterInteractor() {
        return mFilterInteractor;
    }

    public ActiveAppointmentClickListener getActiveAppointmentsClickListener() {
        return mActiveAppointmentsClickListener;
    }

    public NewRecordToBestExpertButtonItemClickListener getNewRecordToBestExpertButtonItemClickListener() {
        return mNewRecordToBestExpertButtonItemClickListener;
    }

    public FindExpertButtonClickListener getFindExpertButtonClickListener() {
        return mFindExpertButtonClickListener;
    }

    public void setMessage(String message) {
        mMessage = new MutableLiveData<>(message);
    }

    @VisibleForTesting
    IExpertsCallBack getExpertsCallBack() {
        return mExpertsCallBack;
    }

    @VisibleForTesting
    IAppointmentsCallback getAppointmentsCallback() {
        return mAppointmentsCallback;
    }

    @VisibleForTesting
    IClientAppointmentCallback getClientAppointmentCallback() {
        return mClientAppointmentCallback;
    }

    @VisibleForTesting
    void setRowTypes(List<RowType> rowTypes) {
        mRowTypes = rowTypes;
    }


}

