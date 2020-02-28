package com.example.client_self_employed.dagger;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.data.IClientRepository;
import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.data.IFileWrapper;
import com.example.client_self_employed.domain.AppointmentInteractor;
import com.example.client_self_employed.domain.ClientInteractor;
import com.example.client_self_employed.domain.DetailedAppointmentInteractor;
import com.example.client_self_employed.domain.ExpertInteractor;

import dagger.Module;
import dagger.Provides;

@Module(includes = DataModule.class)
public class InteractorModule {
    @Provides
    public AppointmentInteractor getAppointmentInteractor(IAppointmentRepository appointmentsRepository) {
        return new AppointmentInteractor(appointmentsRepository);
    }

    @Provides
    public DetailedAppointmentInteractor getDetailedAppointmentInteractor(IAppointmentRepository appointmentsRepository, IExpertRepository expertsRepository) {
        return new DetailedAppointmentInteractor(appointmentsRepository, expertsRepository);
    }

    @Provides
    public ExpertInteractor getExpertInteractor(IExpertRepository expertsRepository) {
        return new ExpertInteractor(expertsRepository);
    }

    @Provides
    public ClientInteractor getClientInteractor(IClientRepository clientRepository, IFileWrapper fileWrapper) {
        return new ClientInteractor(clientRepository, fileWrapper);
    }

}
