package com.example.client_self_employed.dagger;

import android.content.Context;

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

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module(includes = {InteractorModule.class, DataModule.class, ResourceModule.class, ExecutorModule.class, ContextModule.class})
class FactoriesModule {
    @Provides
    HomeScreenModelFactory getHomeScreenModelFactory(AppointmentInteractor iteractor,
                                                     ExpertInteractor expertsInteractor,
                                                     Executor executor) {
        return new HomeScreenModelFactory(iteractor, expertsInteractor, executor);
    }

    @Provides
    DetailedAppointmentViewModelFactory getDetailedAppointmentFactory(DetailedAppointmentInteractor
                                                                              detailedAppointmentInteractor,
                                                                      Executor executor,
                                                                      ResourceWrapper resourceWrapper) {
        return new DetailedAppointmentViewModelFactory(detailedAppointmentInteractor, executor, resourceWrapper);
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
                                                            ExpertInteractor expertInteractor) {
        return new ExpertScheduleViewModelFactory(executor, resourceWrapper, expertInteractor);
    }

    @Provides
    AccountViewModelFactory getAccountFactory(Context context, Executor executor,
                                              ClientInteractor clientInteractor,
                                              ResourceWrapper resourceWrapper) {
        return new AccountViewModelFactory(context, executor, clientInteractor, resourceWrapper);
    }

}
