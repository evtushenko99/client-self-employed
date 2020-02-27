package com.example.client_self_employed.dagger;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.data.RepositoryAppointment;
import com.example.client_self_employed.data.RepositoryClient;
import com.example.client_self_employed.data.RepositoryExpert;
import com.example.client_self_employed.presentation.Utils.ResourceWrapper;

import dagger.Module;
import dagger.Provides;

@Module(includes = ResourceModule.class)
public class RepositoryModule {
    @Provides
    public IExpertRepository getRepositoryExpert(ResourceWrapper resourceWrapper) {
        return new RepositoryExpert(resourceWrapper);
    }

    @Provides
    public IClientRepository getRepositoryClient(ResourceWrapper resourceWrapper) {
        return new RepositoryClient(resourceWrapper);
    }

    @Provides
    public IAppointmentRepository getRepositoryAppointment(ResourceWrapper resourceWrapper) {
        return new RepositoryAppointment(resourceWrapper);
    }


}
