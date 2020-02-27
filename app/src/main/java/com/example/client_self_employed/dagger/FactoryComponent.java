package com.example.client_self_employed.dagger;

import com.example.client_self_employed.presentation.viewmodels.AccountViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.FindExpertViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenModelFactory;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FactoriesModule.class})
public interface FactoryComponent {
    @Singleton
    HomeScreenModelFactory getHomeScreenModelFactory();

    @Singleton
    FindExpertViewModelFactory getFindExpertViewModelFactory();

    @Singleton
    ExpertScheduleViewModelFactory getExpertScheduleViewModelFactory();

    @Singleton
    DetailedAppointmentViewModelFactory getDetailedAppointmentViewModelFactory();

    @Singleton
    AccountViewModelFactory getAccountViewModelFactory();

    void injectHomeScreenFactory(HomeScreenModelFactory homeScreenModelFactory);

    void injectFindExpertFactory(FindExpertViewModelFactory findExpertViewModelFactory);

    void injectExpertScheduleFactory(ExpertScheduleViewModelFactory expertScheduleViewModelFactory);

    void injectDetailedAppointmentFactory(DetailedAppointmentViewModelFactory detailedAppointmentViewModelFactory);

    void injectAccountFactory(AccountViewModelFactory accountViewModelFactory);
}
