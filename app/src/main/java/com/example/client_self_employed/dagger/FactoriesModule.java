package com.example.client_self_employed.dagger;

import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.CreateClientInteractor;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;
import com.example.client_self_employed.domain.FilterActiveAppointmentsInteractor;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;
import com.example.client_self_employed.presentation.createAccount.CreateClientViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.AccountViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.ExpertScheduleViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.FindExpertViewModelFactory;
import com.example.client_self_employed.presentation.viewmodels.HomeScreenModelFactory;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module(includes = {InteractorModule.class, DataModule.class, ResourceModule.class, ExecutorModule.class, ContextModule.class})
class FactoriesModule {
    @Provides
    HomeScreenModelFactory getHomeScreenModelFactory(AppointmentInteractor iteractor,
                                                     ExpertInteractor expertsInteractor,
                                                     FilterActiveAppointmentsInteractor filterInteractor,
                                                     Executor executor) {
        return new HomeScreenModelFactory(iteractor, expertsInteractor, filterInteractor, executor);
    }

    @Provides
    DetailedAppointmentViewModelFactory getDetailedAppointmentFactory(DetailedAppointmentInteractor detailedAppointmentInteractor,
                                                                      FilterActiveAppointmentsInteractor filterInteractor,
                                                                      Executor executor,
                                                                      ResourceWrapper resourceWrapper) {
        return new DetailedAppointmentViewModelFactory(detailedAppointmentInteractor, filterInteractor, executor, resourceWrapper);
    }

    @Provides
    FindExpertViewModelFactory getFindExpertFactory(Executor executor,
                                                    ResourceWrapper resourceWrapper,
                                                    ExpertInteractor expertInteractor) {
        return new FindExpertViewModelFactory(executor, resourceWrapper, expertInteractor);
    }

    @Provides
    ExpertScheduleViewModelFactory getExpertScheduleFactory(Executor executor,
                                                            ResourceWrapper resourceWrapper,
                                                            ExpertInteractor expertInteractor,
                                                            FilterActiveAppointmentsInteractor filterInteractor) {
        return new ExpertScheduleViewModelFactory(executor, resourceWrapper, expertInteractor, filterInteractor);
    }

    @Provides
    AccountViewModelFactory getAccountFactory(Executor executor,
                                              ClientInteractor clientInteractor,
                                              ResourceWrapper resourceWrapper) {
        return new AccountViewModelFactory(executor, clientInteractor, resourceWrapper);
    }

    @Provides
    CreateClientViewModelFactory getCreateClientFactory(Executor executor,
                                                        CreateClientInteractor createClientInteractor,
                                                        ResourceWrapper resourceWrapper) {
        return new CreateClientViewModelFactory(executor, createClientInteractor, resourceWrapper);
    }


}
