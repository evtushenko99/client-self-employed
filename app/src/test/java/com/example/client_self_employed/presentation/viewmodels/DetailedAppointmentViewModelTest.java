package com.example.client_self_employed.presentation.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.impl.utils.SynchronousExecutor;

import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит тесты на {@link DetailedAppointmentViewModel}
 */
public class DetailedAppointmentViewModelTest {
    private DetailedAppointmentViewModel mViewModel;
    private DetailedAppointmentInteractor mInteractor;
    private FilterActiveAppointmentsInteractor mFilterInteractor;
    private ResourceWrapper mResourceWrapper;
    private Appointment mAppointment;
    private Expert mExpert;
    private Calendar mCalendar;
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно
    // @RunWith(PowerMockRunner)
    // @PrepareForTest({ NotificationHandler.class})

    @Before
    public void setUp() {
        mInteractor = mock(DetailedAppointmentInteractor.class);
        mFilterInteractor = mock(FilterActiveAppointmentsInteractor.class);
        mResourceWrapper = mock(ResourceWrapper.class);
        mViewModel = new DetailedAppointmentViewModel(mInteractor, mFilterInteractor, new SynchronousExecutor(), mResourceWrapper);

        mAppointment = createAppointment();
        mExpert = createExpert();
        mCalendar = mock(Calendar.class);
    }

    private Expert createExpert() {
        return mock(Expert.class);
    }

    private Appointment createAppointment() {
        return mock(Appointment.class);
    }

    @Test
    public void bindExpertViews() {
        //arrange
        //act
        mViewModel.bindExpertViews(mExpert);
        //assert
        assertThat(mViewModel.getExpertProfession().getValue(), is(mExpert.getProfession()));
        assertThat(mViewModel.getExpertFullName().getValue(), is(mExpert.getFullName()));
        assertThat(mViewModel.getExpertEmail().getValue(), is(mExpert.getEmail()));
        assertThat(mViewModel.getExpertAge().getValue(), is(mExpert.getAge()));
        assertThat(mViewModel.getExpertWorkExperience().getValue(), is(mExpert.getWorkExperience()));
        assertThat(mViewModel.getExpertPhoneNumber().getValue(), is(mExpert.getPhoneNumber()));

    }


    @Test
    public void bindAppointmentViews() {
        //arrange

        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        //act
        mViewModel.bindAppointmentViews(mAppointment);
        //assert
        assertThat(mViewModel.getAppointmentServiceName().getValue(), is(mAppointment.getServiceName()));
        assertThat(mViewModel.getAppointmentLocation().getValue(), is(mAppointment.getLocation()));
        assertThat(mViewModel.getAppointmentCost().getValue(), is(mAppointment.getCost()));
        assertThat(mViewModel.getAppointmentDate().getValue(), is(mAppointment.getStringDate()));
        assertThat(mViewModel.getAppointmentDuration().getValue(), is(mAppointment.getSessionDuration()));
        assertThat(mViewModel.getAppointmentRating().getValue(), is(mAppointment.getRating()));
        assertThat(mViewModel.getAppointmentStartTime().getValue(), is(mAppointment.getStringTime()));
        assertThat(mViewModel.getAppointmentNotification().getValue(), is(mAppointment.getNotification()));

        verify(mFilterInteractor).makeCalendar(mAppointment);
        assertThat(mViewModel.getTimeCheck().getValue(), is(true));
    }


    @Test
    public void loadDetailedInformation() {
        //arrange
        //act
        mViewModel.loadDetailedInformation(mAppointment.getId(), mExpert.getId());
        //assert
        assertThat(mViewModel.getIsLoadingAppointment().getValue(), is(true));
        assertThat(mViewModel.getIsLoadingExpert().getValue(), is(true));
        verify(mInteractor).loadAppointment(mAppointment.getId(), mViewModel.getAppointmentCallback());
        verify(mInteractor).loadExpert(mExpert.getId(), mViewModel.getExpertCallback());
    }

    @Test
    public void updateAppointmentRating() {
        //arrange
        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        mViewModel.bindAppointmentViews(mAppointment);
        //act
        mViewModel.updateAppointmentRating(mAppointment.getId(), 3);
        //assert
        verify(mInteractor).updateAppointmentRating(mAppointment.getId(), 3, mViewModel.getAppointmentCallback());
    }

    @Test
    public void updateAppointmentNotification() {
        //arrange
        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        mViewModel.bindAppointmentViews(mAppointment);
        //act
        mViewModel.updateAppointmentNotification();
        //assert
        verify(mInteractor).updateAppointmentNotification(mAppointment.getId(),
                !mAppointment.getNotification(),
                mViewModel.getAppointmentCallback());
        assertThat(mViewModel.getAppointmentNotification().getValue(), is(!mAppointment.getNotification()));
    }

    @Test
    public void expertCallback_happyCase() {
        //arrange
        //act
        mViewModel.getExpertCallback().oneExpertIsLoaded(mExpert);
        //assert
        assertThat(mViewModel.getIsLoadingExpert().getValue(), is(false));
        assertThat(mViewModel.getExpert(), is(mExpert));
    }

    @Test
    public void expertCallback_exceptionCase() {
        //arrange
        String message = "message";
        //act
        mViewModel.getExpertCallback().errorLoadOneExpert(message);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(message));
    }

    @Test
    public void appointmentCallback_loadedCase() {
        //arrange
        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        //act
        mViewModel.getAppointmentCallback().oneAppointmentIsLoaded(mAppointment);
        //assert
        assertThat(mViewModel.getAppointment(), is(mAppointment));
    }

    @Test
    public void appointmentCallback_exceptionCase() {
        //arrange
        String message = "message";
        //act
        mViewModel.getAppointmentCallback().errorLoadOneAppointment(message);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(message));

    }

    @Test
    public void appointmentCallback_updateCase() {
        //arrange
        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        mViewModel.bindExpertViews(mExpert);
        mViewModel.bindAppointmentViews(mAppointment);
        //act
        mViewModel.getAppointmentCallback().onUpdateCallback(true);
        //assert
        assertThat(mViewModel.getIsLoadingAppointment().getValue(), is(true));
        assertThat(mViewModel.getIsLoadingExpert().getValue(), is(true));
        verify(mInteractor).loadAppointment(mAppointment.getId(), mViewModel.getAppointmentCallback());
        verify(mInteractor).loadExpert(mExpert.getId(), mViewModel.getExpertCallback());
    }
   /*
    };
    private ILoadOneAppointmentCallback mAppointmentCallback = new ILoadOneAppointmentCallback() {
        @Override
        public void oneAppointmentIsLoaded(Appointment appointment) {
            mIsLoadingAppointment.postValue(false);
            if (appointment != null) {
                bindAppointmentViews(appointment);
                if (isUpdateButtonText() && appointment.getNotification()) {
                    updateAppointmentNotification();
                    mUpdateButtonText = false;
                }
            }
        }

    };*/

  /*  @Test
    public void createNotification() {
        //arrange
        mockStatic()
        mViewModel.bindAppointmentViews(mAppointment);
        when(mInteractor.createWorkInputData(mAppointment.getServiceName(), mAppointment.getStringTime(), mAppointment.getId(), mExpert.getId()))
                .thenReturn(mock(Data))
        //act
        mViewModel.createNotification();
        //assert

    }*/

    /*
    public void createNotification(){
            int secondsBeforAlert=10;
            // long current = System.currentTimeMillis();
            Data data=mInteractor.createWorkInputData(serviceName,startTime,appointmentId,expertId);
            NotificationHandler.schedulerReminder(secondsBeforAlert,data,String.valueOf(appointmentId));
            updateAppointmentNotification();
            }
    */
   /* @Test
    public void setCancelAppointmentClickListener() {
        //arrange
        IAddNotificationClickListener addNotificationClickListener = mock(IAddNotificationClickListener.class);
        Calendar mCalendar = mock(Calendar.class);
        when(mFilterInteractor.makeCalendar(mAppointment)).thenReturn(mCalendar);
        mViewModel.bindAppointmentViews(mAppointment);
        //act
        mViewModel.setNotificationClickListener(addNotificationClickListener);
        //
        verify(addNotificationClickListener).onRemoveNotificationClickListenrs(mAppointment.getId());

    }*/
/*

    public void setNotificationClickListener(IAddNotificationClickListener addNotificationClickListener){
            mNotificationClickListener=v->{
            if(mAppointmentNotification.getValue()){
            addNotificationClickListener.onRemoveNotificationClickListenrs(mAppointment.getId());
            }else{
            addNotificationClickListener.onAddNotificationClickListener();
            }
            };
            }
    */
   /* @Test
    public void setNotificationClickListener() {
    }


    public void setNotificationClickListener(IAddNotificationClickListener addNotificationClickListener){
            mNotificationClickListener=v->{
            if(mAppointmentNotification.getValue()){
            addNotificationClickListener.onRemoveNotificationClickListenrs(mAppointment.getId());
            }else{
            addNotificationClickListener.onAddNotificationClickListener(mAppointment.getServiceName(),mAppointment.getStringTime(),mAppointment.getId(),mExpert.getId());
            }
            };
            }
    */
}