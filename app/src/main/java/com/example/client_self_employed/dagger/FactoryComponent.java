package com.example.client_self_employed.dagger;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.FindExpertViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {InteractorModule.class, RepositoryModule.class, ResourceModule.class})
public interface FactoryComponent {
    void injectHomeScreenFactory(HomeScreenModelFactory homeScreenModelFactory);

    void injectFindExpertFactory(FindExpertViewModelFactory findExpertViewModelFactory);

    void injectExpertScheduleFactory(ExpertScheduleViewModelFactory expertScheduleViewModelFactory);

    void injectDetailedAppointmentFactory(DetailedAppointmentViewModelFactory detailedAppointmentViewModelFactory);

    void injectAccountFactory(AccountViewModelFactory accountViewModelFactory);

    IAppointmentRepository getAppointmentRepository();

    IExpertRepository getExpertsRepository();

    IClientRepository getClientRepository();

    AppointmentInteractor getAppointmentInteractor();

    ClientInteractor getClientInteractor();

    DetailedAppointmentInteractor getDetailedAppointmentInteractor();

    ExpertInteractor getExpertInteractor();

    ResourceWrapper getResourceWrapper();
}
