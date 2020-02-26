package com.example.client_self_employed.presentation.viewmodels;

import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.CheckedDateOfAppointmentsInteractor;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.ILoadOneAppointmentCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.clicklisteners.RatingClickListeners;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IAddNotificationClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.ICallPhoneClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.ICancelAppointmentClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IWhatsAppClickListeners;

import java.util.Calendar;
import java.util.concurrent.Executor;

public class DetailedAppointmentViewModel extends ViewModel {
    private final DetailedAppointmentInteractor mInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;

    public MutableLiveData<String> getErrors() {
        return mErrors;
    }

    public LiveData<String> getLiveDataErrors() {
        return mErrors;
    }

    private final MutableLiveData<String> mErrors = new MutableLiveData<>();

    private MutableLiveData<Boolean> mIsLoadingExpert = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mIsLoadingAppointment = new MutableLiveData<>(false);
    private ObservableField<String> mExpertProfession = new ObservableField<>();
    private ObservableField<String> mExpertFullName = new ObservableField<>();
    private ObservableField<String> mExpertPhoneNumber = new ObservableField<>();
    private ObservableField<String> mExpertEmail = new ObservableField<>();
    private ObservableInt mExpertAge = new ObservableInt();
    private ObservableInt mExpertWorkExperience = new ObservableInt();

    private ObservableField<String> mAppointmentServiceName = new ObservableField<>();
    private ObservableField<String> mAppointmentDate = new ObservableField<>();
    private ObservableField<String> mAppointmentDuration = new ObservableField<>();
    private ObservableField<String> mAppointmentLocation = new ObservableField<>();
    private ObservableField<String> mAppointmentStartTime = new ObservableField<>();
    private ObservableField<Float> mAppointmentRating = new ObservableField<>();

    private ObservableInt mAppointmentCost = new ObservableInt();
    private ObservableField<Boolean> mAdditionalInformationAboutExpert = new ObservableField<>();
    private ObservableField<String> mMoreInformationTextView = new ObservableField<>();
    private ObservableField<Appointment> mObservableAppointment = new ObservableField<>();

    public Appointment getAppointment() {
        return mAppointment;
    }

    private Appointment mAppointment;

    private Expert mExpert;


    private View.OnClickListener mCancelAppointmentClickListener;
    private View.OnClickListener mAddNotificationClickListener;

    private View.OnClickListener mCallPhoneClickListener;
    private View.OnClickListener mWhatsAppClickListener;
    private View.OnClickListener mViberClickListener;
    private View.OnClickListener mTelegrammClickListener;
    private View.OnClickListener mMoreInformationClickListener;
    private RatingClickListeners mRatingClickListener;


    private ILoadOneExpertCallback mExpertCallback = new ILoadOneExpertCallback() {
        @Override
        public void oneExpertIsLoaded(Expert expert) {
            mIsLoadingExpert.postValue(false);
            if (expert != null) {
                mExpert = expert;
                bindExpertViews(expert);
            }
        }

        @Override
        public void errorLoadOneExpert(String errorLoadOneExpert) {

        }
    };
    private ILoadOneAppointmentCallback mAppointmentCallback = new ILoadOneAppointmentCallback() {
        @Override
        public void oneAppointmentIsLoaded(Appointment appointment) {
            mIsLoadingAppointment.postValue(false);
            if (appointment != null) {
                mAppointment = appointment;

                mObservableAppointment.set(mAppointment);
                bindAppointmentViews(appointment);
            }
        }

        @Override
        public void errorLoadOneAppointment(String errorLoadOneAppointment) {

        }

        @Override
        public void onUpdateCallback(boolean isRatingUpdate) {
            if (isRatingUpdate) {
                loadDetailedInformation(mAppointment.getId(), mExpert.getId());
            }
        }
    };

    public DetailedAppointmentViewModel(
            @NonNull DetailedAppointmentInteractor interactor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mInteractor = interactor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
        mAdditionalInformationAboutExpert.set(false);
        mMoreInformationTextView.set(mResourceWrapper.getString(R.string.fragment_detailed_information_expert_more_inf));
    }

    public void loadDetailedInformation(@NonNull Long appointmentId, @NonNull Long expertId) {
        mIsLoadingAppointment.setValue(true);
        mIsLoadingExpert.setValue(true);
        mExecutor.execute(() ->
                mInteractor.loadAppointment(appointmentId, mAppointmentCallback));
        mExecutor.execute(() ->
                mInteractor.loadExpert(expertId, mExpertCallback));
    }

    public void updateAppointmentRating(@NonNull Long appointmentId, float rating) {
        mExecutor.execute(() -> mInteractor.updateAppointmentRating(appointmentId, rating, mAppointmentCallback));
    }

    private void bindExpertViews(@NonNull Expert expert) {
        mExpertProfession.set(expert.getProfession());
        mExpertFullName.set(expert.getFullName());
        mExpertEmail.set(expert.getEmail());
        mExpertAge.set(expert.getAge());
        mExpertWorkExperience.set(expert.getWorkExperience());
        mExpertPhoneNumber.set(expert.getPhoneNumber());
    }

    private void bindAppointmentViews(@NonNull Appointment appointment) {
        mAppointmentServiceName.set(appointment.getServiceName());
        mAppointmentLocation.set(appointment.getLocation());
        mAppointmentCost.set(appointment.getCost());
        mAppointmentDate.set(appointment.getStringDate());
        mAppointmentDuration.set(appointment.getSessionDuration());
        mAppointmentStartTime.set(appointment.getStringTime());
        mAppointmentRating.set(appointment.getRating());
    }

    public void setCancelAppointmentClickListener(ICancelAppointmentClickListener cancelAppointmentClickListener) {
        mCancelAppointmentClickListener = v -> {
            if (mAppointment != null) {
                cancelAppointmentClickListener.onCancelAppointmentClickListener(mAppointment.getId());
            }
        };
    }

    public View.OnClickListener getCancelAppointmentClickListener() {
        return mCancelAppointmentClickListener;
    }


    public void setAddNotificationClickListener(IAddNotificationClickListener addNotificationClickListener) {
        mAddNotificationClickListener = v -> {
            if (mAppointment != null) {
                addNotificationClickListener.onAddNotificationClickListener(mAppointment.getServiceName(), mAppointment.getStringTime(), mAppointment.getId());
            }
        };
    }

    public View.OnClickListener getCallPhoneClickListener() {
        return mCallPhoneClickListener;
    }

    public void setCallPhoneClickListener(ICallPhoneClickListener callPhoneClickListener) {
        mCallPhoneClickListener = v -> {
            if (mExpert != null) {
                callPhoneClickListener.onCallPhoneClickListener(mExpert.getPhoneNumber());
            }
        };
    }

    public View.OnClickListener getWhatsAppClickListener() {
        return mWhatsAppClickListener;
    }

    public void setWhatsAppClickListener(IWhatsAppClickListeners whatsAppClickListener) {
        mWhatsAppClickListener = v -> whatsAppClickListener.onWhatsAppClickListeners(mExpert.getPhoneNumber());
    }

    public View.OnClickListener getViberClickListener() {
        return mViberClickListener;
    }

    public void setViberClickListener(View.OnClickListener viberClickListener) {
        mViberClickListener = viberClickListener;
    }

    public View.OnClickListener getTelegrammClickListener() {
        return mTelegrammClickListener;
    }

    public void settelegramClickListener(View.OnClickListener mtelegramClickListener) {
        this.mTelegrammClickListener = mtelegramClickListener;
    }

    public View.OnClickListener getMoreInformationClickListener() {
        return mMoreInformationClickListener;
    }

    public void setMoreInformationClickListener(View.OnClickListener moreInformationClickListener) {
        mMoreInformationClickListener = moreInformationClickListener;
    }


    public View.OnClickListener getAddNotificationClickListener() {
        return mAddNotificationClickListener;
    }


    public ObservableField<Float> getAppointmentRating() {
        return mAppointmentRating;
    }

    public void setAppointmentRating(ObservableField<Float> appointmentRating) {
        mAppointmentRating = appointmentRating;
    }


    public ObservableField<String> getMoreInformationTextView() {
        return mMoreInformationTextView;
    }

    public void setMoreInformationTextView(String moreInformationTextView) {
        mMoreInformationTextView.set(moreInformationTextView);
    }


    public RatingClickListeners getRatingClickListener() {
        return mRatingClickListener;
    }

    public void setRatingClickListener(RatingClickListeners ratingClickListener) {
        mRatingClickListener = ratingClickListener;
    }


    @BindingAdapter(value = {"RatingChanged", "GetRating", "GetAppointment", "GetError"}, requireAll = false)
    public static void setListeners(RatingBar view, RatingClickListeners listener, float oldRating, Appointment appointment, String error) {
        view.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (appointment != null) {
                    Calendar calendar = CheckedDateOfAppointmentsInteractor.makeCalendar(appointment);
                    if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                        if (oldRating != rating) {
                            //    error.postValue("Вы не можете оценить данную запись, так как она еще не началась");
                            listener.onRatingClickListeners(rating);
                        }
                    } else {

                        ratingBar.setRating(0);
                    }
                }
            }
        });

    }

    /**
     * Состояние для определения загрузки информации об эксперте
     */
    public MutableLiveData<Boolean> getIsLoadingExpert() {
        return mIsLoadingExpert;
    }

    /**
     * Состояние для определения загрузки информации об активной записи
     *
     * @return
     */
    public MutableLiveData<Boolean> getIsLoadingAppointment() {
        return mIsLoadingAppointment;
    }

    public ObservableField<String> getExpertProfession() {
        return mExpertProfession;
    }

    public ObservableField<String> getExpertFullName() {
        return mExpertFullName;
    }

    public ObservableField<String> getExpertPhoneNumber() {
        return mExpertPhoneNumber;
    }

    public ObservableField<String> getExpertEmail() {
        return mExpertEmail;
    }

    public ObservableInt getExpertAge() {
        return mExpertAge;
    }

    public ObservableInt getExpertWorkExperience() {
        return mExpertWorkExperience;
    }

    public ObservableField<String> getAppointmentServiceName() {
        return mAppointmentServiceName;
    }

    public ObservableField<String> getAppointmentDate() {
        return mAppointmentDate;
    }

    public ObservableField<String> getAppointmentDuration() {
        return mAppointmentDuration;
    }

    public ObservableField<String> getAppointmentLocation() {
        return mAppointmentLocation;
    }

    public ObservableField<String> getAppointmentStartTime() {
        return mAppointmentStartTime;
    }

    public ObservableInt getAppointmentCost() {
        return mAppointmentCost;
    }

    public ObservableField<Boolean> getAdditionalInformationAboutExpert() {
        return mAdditionalInformationAboutExpert;
    }

    public void setAdditionalInformationAboutExpert(boolean additionalInformationAboutExpert) {
        mAdditionalInformationAboutExpert.set(additionalInformationAboutExpert);
    }

    public ObservableField<Appointment> getObservableAppointment() {
        return mObservableAppointment;
    }

    public void setObservableAppointment(ObservableField<Appointment> observableAppointment) {
        mObservableAppointment = observableAppointment;
    }


}
