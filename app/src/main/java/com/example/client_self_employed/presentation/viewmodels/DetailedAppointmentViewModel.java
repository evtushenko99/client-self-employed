package com.example.client_self_employed.presentation.viewmodels;

import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;

import com.example.client_self_employed.R;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.ILoadOneAppointmentCallback;
import com.example.client_self_employed.domain.ILoadOneExpertCallback;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.notification.NotificationHandler;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.clicklisteners.RatingClickListeners;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IAddNotificationClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.ICallPhoneClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.ICancelAppointmentClickListener;
import com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners.IWhatsAppClickListeners;

import java.util.Calendar;
import java.util.concurrent.Executor;


public class DetailedAppointmentViewModel extends ViewModel {
    //Обновить текст кнопки уведомлений, когда пользователь нажимает на уведомление
    private boolean mUpdateButtonText = false;

    private final DetailedAppointmentInteractor mInteractor;
    private final FilterActiveAppointmentsInteractor mFilterInteractor;
    private final Executor mExecutor;
    private final ResourceWrapper mResourceWrapper;
    private final MutableLiveData<String> mMessage = new MutableLiveData<>();

    private MutableLiveData<Boolean> mIsLoadingExpert = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mIsLoadingAppointment = new MutableLiveData<>(false);
    private MutableLiveData<String> mExpertProfession = new MutableLiveData<>();
    private MutableLiveData<String> mExpertFullName = new MutableLiveData<>();
    private MutableLiveData<String> mExpertPhoneNumber = new MutableLiveData<>();
    private MutableLiveData<String> mExpertEmail = new MutableLiveData<>();
    private MutableLiveData<Integer> mExpertAge = new MutableLiveData();
    private MutableLiveData<Integer> mExpertWorkExperience = new MutableLiveData();

    private MutableLiveData<String> mAppointmentServiceName = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentDate = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentDuration = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentLocation = new MutableLiveData<>();
    private MutableLiveData<String> mAppointmentStartTime = new MutableLiveData<>();

    private MutableLiveData<Float> mAppointmentRating = new MutableLiveData<>();
    private MutableLiveData<Boolean> mTimeCheck = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mAppointmentNotification = new MutableLiveData<>();


    private MutableLiveData<Integer> mAppointmentCost = new MutableLiveData();
    private MutableLiveData<Boolean> mAdditionalInformationAboutExpert = new MutableLiveData<>();
    private MutableLiveData<String> mMoreInformationTextView = new MutableLiveData<>();
    //private MutableLiveData<Appointment> mObservableAppointment = new MutableLiveData<>();
    //Запись, информация о которой отображается на экране FragmentDetailedAppointment
    private Appointment mAppointment;
    //Эксперт, информация о котором отображается на экране FragmentDetailedAppointment
    private Expert mExpert;
    //Слушатель для отмены активной записи
    private View.OnClickListener mCancelAppointmentClickListener;
    //Слушатель для добавления или удаления уведомления
    private View.OnClickListener mNotificationClickListener;
    ;
    //слушатели, которые отвечают за связь с экспертом через сторонние приложения
    private View.OnClickListener mCallPhoneClickListener;
    private View.OnClickListener mWhatsAppClickListener;
    private View.OnClickListener mViberClickListener;
    private View.OnClickListener mTelegrammClickListener;
    // Слушаель, который за предоставление дополнительной информации об эксперте
    private View.OnClickListener mMoreInformationClickListener;
    // Слушатель, который отвечает за оценку оказанной услуги клиенту,
    // появляется только у записи, которая началась или уже прошла
    private RatingClickListeners mRatingClickListener;
    private ILoadOneExpertCallback mExpertCallback = new ILoadOneExpertCallback() {
        @Override
        public void oneExpertIsLoaded(Expert expert) {
            mIsLoadingExpert.postValue(false);
            if (expert != null) {
                bindExpertViews(expert);
            }
        }

        @Override
        public void messageLoadOneExpert(String messageLoadOneExpert) {
            mMessage.postValue(messageLoadOneExpert);
        }
    };
    private ILoadOneAppointmentCallback mAppointmentCallback = new ILoadOneAppointmentCallback() {
        @Override
        public void oneAppointmentIsLoaded(Appointment appointment) {
            mIsLoadingAppointment.postValue(false);
            if (appointment != null) {
                bindAppointmentViews(appointment);
                if (isUpdateButtonText() && appointment.getNotification()) {
                    mUpdateButtonText = false;
                }
            }
        }

        @Override
        public void messageLoadOneAppointment(String errorLoadOneAppointment) {
            mMessage.postValue(errorLoadOneAppointment);
        }

        @Override
        public void onUpdateCallback(boolean isRatingUpdate) {
            if (isRatingUpdate) {
                loadDetailedInformation(mAppointment.getId(), mExpert.getId());
            }
        }
    };

    DetailedAppointmentViewModel(
            @NonNull DetailedAppointmentInteractor interactor,
            @NonNull FilterActiveAppointmentsInteractor filterInteractor,
            @NonNull Executor executor,
            @NonNull ResourceWrapper resourceWrapper) {
        mInteractor = interactor;
        mFilterInteractor = filterInteractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
        mAdditionalInformationAboutExpert.setValue(false);
        mMoreInformationTextView.setValue(mResourceWrapper.getString(R.string.fragment_detailed_information_expert_more_inf));
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
        if (mAppointmentRating.getValue() != rating) {
            mExecutor.execute(() -> mInteractor.updateAppointmentRating(appointmentId, rating, mAppointmentCallback));
        }
    }

    public void bindExpertViews(@NonNull Expert expert) {
        mExpert = expert;
        mExpertProfession.setValue(expert.getProfession());
        mExpertFullName.setValue(expert.getFullName());
        mExpertEmail.setValue(expert.getEmail());
        mExpertAge.setValue(expert.getAge());
        mExpertWorkExperience.setValue(expert.getWorkExperience());
        mExpertPhoneNumber.setValue(expert.getPhoneNumber());
    }

    public void bindAppointmentViews(@NonNull Appointment appointment) {
        mAppointment = appointment;
        mAppointmentServiceName.setValue(appointment.getServiceName());
        mAppointmentLocation.setValue(appointment.getLocation());
        mAppointmentCost.setValue(appointment.getCost());
        mAppointmentDate.setValue(appointment.getStringDate());
        mAppointmentDuration.setValue(appointment.getSessionDuration());
        mAppointmentStartTime.setValue(appointment.getStringTime());
        mAppointmentRating.setValue(appointment.getRating());
        mAppointmentNotification.setValue(appointment.getNotification());
        Calendar calendar = mFilterInteractor.makeCalendar(appointment);
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            mTimeCheck.setValue(true);
        } else {
            mTimeCheck.setValue(false);
        }
    }

    public void createNotification() {
        int secondsBeforAlert = 10;
        // long current = System.currentTimeMillis();
        Data data = mInteractor.createWorkInputData(mAppointment.getServiceName(), mAppointment.getStringTime(), mAppointment.getId(), mExpert.getId());
        NotificationHandler.schedulerReminder(secondsBeforAlert, data, String.valueOf(mAppointment.getId()));
        updateAppointmentNotification();
    }

    public void setCancelAppointmentClickListener(ICancelAppointmentClickListener cancelAppointmentClickListener) {
        mCancelAppointmentClickListener = v -> {
            if (mAppointment != null) {
                cancelAppointmentClickListener.onCancelAppointmentClickListener(mAppointment.getId());
            }
        };
    }

    public void setNotificationClickListener(IAddNotificationClickListener addNotificationClickListener) {
        mNotificationClickListener = v -> {
            if (mAppointmentNotification.getValue()) {
                addNotificationClickListener.onRemoveNotificationClickListenrs(mAppointment.getId());
            } else {
                addNotificationClickListener.onAddNotificationClickListener();
            }
        };
    }

    /**
     * Метод, обновляющий значение поля AppointmentNotificatioн на сервере, если была нажата
     * добавить или удалить уведомление (обновление которой приводит к изменению текста кнопки)
     */
    public void updateAppointmentNotification() {
        mExecutor.execute(() -> {
            if (mAppointment != null) {
                mAppointmentNotification.postValue(!mAppointmentNotification.getValue());
                mInteractor.updateAppointmentNotification(mAppointment.getId(), mAppointmentNotification.getValue(), mAppointmentCallback);
            }
        });

    }

    @BindingAdapter("android:text")
    public static void setText(AppCompatButton view, boolean notification) {
        if (notification) {
            view.setText(R.string.fragment_detailed_appointment_delete_notification);
        } else {
            view.setText(R.string.fragment_detailed_appointment_add_notification);
        }
    }

    @BindingAdapter("android:drawableEnd")
    public static void setDrawable(AppCompatButton view, boolean notification) {
        if (notification) {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, view.getResources().getDrawable(R.drawable.ic_clear_black_24dp, null), null);
        } else {
            view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }


    @BindingAdapter("rating")
    public static void setRating(RatingBar view, float rating) {
        if (view.getRating() != rating) {
            view.setRating(rating);
        }
    }

    @BindingAdapter(value = {"android:onRatingChanged"}, requireAll = false)
    public static void setListeners(RatingBar view, final RatingClickListeners listener) {
        view.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (listener != null) {
                listener.onRatingClickListeners(rating);
            }
        });

    }

    public void setCallPhoneClickListener(ICallPhoneClickListener callPhoneClickListener) {
        mCallPhoneClickListener = v -> {
            if (mExpert != null) {
                callPhoneClickListener.onCallPhoneClickListener(mExpert.getPhoneNumber());
            }
        };
    }

    public void setWhatsAppClickListener(IWhatsAppClickListeners whatsAppClickListener) {
        mWhatsAppClickListener = v -> whatsAppClickListener.onWhatsAppClickListeners(mExpert.getPhoneNumber());
    }

    public void setViberClickListener(View.OnClickListener viberClickListener) {
        mViberClickListener = viberClickListener;
    }

    public void setTelegramClickListener(View.OnClickListener mtelegramClickListener) {
        this.mTelegrammClickListener = mtelegramClickListener;
    }

    public void setMoreInformationClickListener(View.OnClickListener moreInformationClickListener) {
        mMoreInformationClickListener = moreInformationClickListener;
    }

    public void setMoreInformationTextView(String moreInformationTextView) {
        mMoreInformationTextView.setValue(moreInformationTextView);
    }

    public void setAdditionalInformationAboutExpert(boolean additionalInformationAboutExpert) {
        mAdditionalInformationAboutExpert.setValue(additionalInformationAboutExpert);
    }

    public void setUpdateButtonText(boolean updateButtonText) {
        mUpdateButtonText = updateButtonText;
    }

    public void setRatingClickListener(RatingClickListeners ratingClickListener) {
        mRatingClickListener = ratingClickListener;
    }

    public LiveData<Boolean> getTimeCheck() {
        return mTimeCheck;
    }

    public LiveData<Boolean> getAppointmentNotification() {
        return mAppointmentNotification;
    }

    public View.OnClickListener getCallPhoneClickListener() {
        return mCallPhoneClickListener;
    }

    public View.OnClickListener getCancelAppointmentClickListener() {
        return mCancelAppointmentClickListener;
    }

    public View.OnClickListener getWhatsAppClickListener() {
        return mWhatsAppClickListener;
    }

    public View.OnClickListener getViberClickListener() {
        return mViberClickListener;
    }

    public View.OnClickListener getTelegramClickListener() {
        return mTelegrammClickListener;
    }

    public View.OnClickListener getMoreInformationClickListener() {
        return mMoreInformationClickListener;
    }

    public View.OnClickListener getNotificationClickListener() {
        return mNotificationClickListener;
    }

    public LiveData<Float> getAppointmentRating() {
        return mAppointmentRating;
    }


    /**
     * Состояние для определения загрузки информации об эксперте
     */
    public MutableLiveData<Boolean> getIsLoadingExpert() {
        return mIsLoadingExpert;
    }

    /**
     * Состояние для определения загрузки информации об активной записи
     */
    public MutableLiveData<Boolean> getIsLoadingAppointment() {
        return mIsLoadingAppointment;
    }

    public LiveData<String> getExpertProfession() {
        return mExpertProfession;
    }

    public LiveData<String> getExpertFullName() {
        return mExpertFullName;
    }

    public LiveData<String> getExpertPhoneNumber() {
        return mExpertPhoneNumber;
    }

    public LiveData<String> getExpertEmail() {
        return mExpertEmail;
    }

    public LiveData<Integer> getExpertAge() {
        return mExpertAge;
    }

    public LiveData<Integer> getExpertWorkExperience() {
        return mExpertWorkExperience;
    }

    public LiveData<String> getAppointmentServiceName() {
        return mAppointmentServiceName;
    }

    public LiveData<String> getAppointmentDate() {
        return mAppointmentDate;
    }

    public LiveData<String> getAppointmentDuration() {
        return mAppointmentDuration;
    }

    public LiveData<String> getAppointmentLocation() {
        return mAppointmentLocation;
    }

    public LiveData<String> getAppointmentStartTime() {
        return mAppointmentStartTime;
    }

    public LiveData<Integer> getAppointmentCost() {
        return mAppointmentCost;
    }

    public LiveData<String> getMessage() {
        return mMessage;
    }

    public LiveData<String> getMoreInformationTextView() {
        return mMoreInformationTextView;
    }

    public LiveData<Boolean> getAdditionalInformationAboutExpert() {
        return mAdditionalInformationAboutExpert;
    }

    private boolean isUpdateButtonText() {
        return mUpdateButtonText;
    }

    public RatingClickListeners getRatingClickListener() {
        return mRatingClickListener;
    }

    @VisibleForTesting
    ILoadOneExpertCallback getExpertCallback() {
        return mExpertCallback;
    }

    @VisibleForTesting
    ILoadOneAppointmentCallback getAppointmentCallback() {
        return mAppointmentCallback;
    }

    @VisibleForTesting
    public Appointment getAppointment() {
        return mAppointment;
    }

    @VisibleForTesting
    public Expert getExpert() {
        return mExpert;
    }

}
