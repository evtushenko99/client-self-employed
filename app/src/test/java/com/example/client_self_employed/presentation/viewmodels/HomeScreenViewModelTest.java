package com.example.client_self_employed.presentation.viewmodels;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.impl.utils.SynchronousExecutor;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;
import com.example.client_self_employed.presentation.Utils.ModelsConverter;
import com.example.client_self_employed.presentation.adapters.items.ClientActiveAppointmentsItem;
import com.example.client_self_employed.presentation.adapters.items.ClientExpertItem;
import com.example.client_self_employed.presentation.adapters.items.ClientNoAppointmentItem;
import com.example.client_self_employed.presentation.adapters.items.RowType;
import com.example.client_self_employed.presentation.model.ClientAppointment;
import com.example.client_self_employed.presentation.model.ClientSelectedExpert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит тесты на {@link HomeScreenViewModel}
 */
public class HomeScreenViewModelTest {
    private AppointmentInteractor mAppointmentInteractor;
    private ExpertInteractor mExpertsInteractor;
    private FilterActiveAppointmentsInteractor mFilterInteractor;
    private HomeScreenViewModel mViewModel;
    private ModelsConverter mModelsConverter;
    private List<RowType> mRowTypeList;
    private String mException = "exception";
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule(); // выполнение каждой задачи синхронно

    @Before
    public void setUp() {
        mRowTypeList = new ArrayList<>(Arrays.asList(mock(RowType.class)));
        mAppointmentInteractor = mock(AppointmentInteractor.class);
        mExpertsInteractor = mock(ExpertInteractor.class);
        mFilterInteractor = mock(FilterActiveAppointmentsInteractor.class);
        mModelsConverter = mock(ModelsConverter.class);
        mViewModel = new HomeScreenViewModel(
                mAppointmentInteractor,
                mExpertsInteractor,
                mFilterInteractor,
                new SynchronousExecutor()
        );
    }

    @Test
    public void appointmentCallback_happyCase() {
        //arrange
        List<Appointment> appointments = new ArrayList<>(Arrays.asList(mock(Appointment.class)));
        List<String> expertsId = new ArrayList<>(Arrays.asList("1", "2"));
        //act
        mViewModel.getAppointmentsCallback().onAppointmentCallback(appointments, expertsId);
        //assert
        verify(mExpertsInteractor).loadExperNameForActiveAppointment(appointments, expertsId, mViewModel.getClientAppointmentCallback());
    }

    @Test
    public void appointmentCallback_exceptionCase() {
        //arrange
        //act
        mViewModel.getAppointmentsCallback().onErrorLoadingActiveAppointments(mException);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(mException));
    }

    @Test
    public void expertCallbackTest_happyCase() {
        //arrange
        List<Expert> experts = new ArrayList<>(createExpertList());
        List<RowType> expected = new ArrayList<>(createExpectedRowType(experts));
        mViewModel.setCount(experts.size() - 1);
        //act
        mViewModel.getExpertsCallBack().expertsIsLoaded(experts);
        //assert
        assertThat(mViewModel.getIsBestExpertLoading().getValue(), is(false));
        assertThat(mViewModel.getLiveData().getValue(), is(expected));

        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(true));
        verify(mAppointmentInteractor).loadClientsAppointments(2, mViewModel.getAppointmentsCallback());
    }

    @Test
    public void expertCallbackTest_exceptionCase() {
        //arrange
        //act
        mViewModel.getExpertsCallBack().messageLoadingExperts(mException);
        String actual = mViewModel.getMessage().getValue();
        //assert
        assertThat(mViewModel.getIsBestExpertLoading().getValue(), is(false));
        assertThat(actual, is(mException));
    }

    private List<RowType> createExpectedRowType(List<Expert> experts) {
        List<RowType> expectedRowType = new ArrayList<>();
        List<ClientSelectedExpert> list = new ArrayList<>();
        if (experts.size() != 0) {
            for (Expert expert : experts) {
                list.add(new ClientSelectedExpert(expert.getId(), expert.getAbbreviatedFullName(), expert.getExpertPhotoUri()));
            }
            ClientExpertItem item = new ClientExpertItem(list);
            expectedRowType.add(item);
        }
        return expectedRowType;
    }

    private List<Expert> createExpertList() {
        List<Expert> experts = new ArrayList<>();
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5, null, 10, 50);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2, null, 10, 50);
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10, null, 10, 50);
        experts.add(expert1);
        experts.add(expert2);
        experts.add(expert3);
        return experts;

    }

    @Test
    public void clientAppointmentCallback_happyCase() {
        //arrange
        List<Appointment> appointmentList = new ArrayList<>(Arrays.asList(mock(Appointment.class)));
        List<Expert> experts = new ArrayList<>(Arrays.asList(mock(Expert.class)));

        List<RowType> expected = new ArrayList<>(Arrays.asList(mock(ClientExpertItem.class)));
        expected.add(new ClientActiveAppointmentsItem(convertAppointmentToRowType()));

        when(mModelsConverter.convertAppointmentToRowType(appointmentList, experts)).thenReturn(convertAppointmentToRowType());
        //act
        mViewModel.setRowTypes(Arrays.asList(mock(ClientExpertItem.class)));
        mViewModel.getClientAppointmentCallback().clientsAppointmentsIsLoaded(appointmentList, experts);
        //assert
        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(false));
        // assertThat(mViewModel.getLiveData().getValue(), is(expected));
    }

    private List<ClientAppointment> convertAppointmentToRowType() {
        return new ArrayList<>(Arrays.asList(mock(ClientAppointment.class)));
    }

    @Test
    public void clientAppointmentCallback_happyCase_withNoActiveAppointments() {
        //arrange
        List<Appointment> appointmentList = new ArrayList<>();
        List<Expert> experts = new ArrayList<>();

        List<RowType> expected = new ArrayList<>(Arrays.asList(mock(ClientExpertItem.class)));
        expected.add(new ClientNoAppointmentItem());
        //act
        mViewModel.setRowTypes(Arrays.asList(mock(ClientExpertItem.class)));
        mViewModel.getClientAppointmentCallback().clientsAppointmentsIsLoaded(appointmentList, experts);
        //assert
        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(false));
        //assertThat(mViewModel.getLiveData().getValue(), is(expected));
    }


    @Test
    public void clientAppointmentCallback_happyRemovalCase() {
        //arrange
        //act
        mViewModel.getClientAppointmentCallback().clientAppointmentIsDeleted(true);
        //assert
        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(true));
        verify(mAppointmentInteractor).loadClientsAppointments(2, mViewModel.getAppointmentsCallback());

    }

    @Test
    public void clientAppointmentCallback_exceptionCase() {
        //arrange
        //act
        mViewModel.getClientAppointmentCallback().message(mException);
        //assert
        assertThat(mViewModel.getMessage().getValue(), is(mException));
        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(false));
    }

    @Test
    public void loadClientExperts() {
        //arrange
        //act
        mViewModel.loadClientExperts();
        //assert
        assertThat(mViewModel.getIsBestExpertLoading().getValue(), is(true));
        verify(mExpertsInteractor, times(2)).loadAllExperts(mViewModel.getExpertsCallBack());

    }

    @Test
    public void loadActiveAppointments() {
        //arrange
        //act
        mViewModel.loadActiveAppointments();
        //assert
        assertThat(mViewModel.getIsActiveAppointmentLoading().getValue(), is(true));
        verify(mAppointmentInteractor).loadClientsAppointments(2, mViewModel.getAppointmentsCallback());
    }

    @Test
    public void deleteClientAppointment() {
        //arrange
        long appointmentId = 2;
        //acr
        mViewModel.deleteClientAppointment(appointmentId);
        //assert
        verify(mAppointmentInteractor).deleteClientAppointment(appointmentId, mViewModel.getClientAppointmentCallback());
    }

    @Test
    public void getFilterInteractor() {
        //act
        FilterActiveAppointmentsInteractor interactor = mViewModel.getFilterInteractor();
        //assert
        assertThat(interactor, is(mFilterInteractor));
    }

    /*
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
     */
}