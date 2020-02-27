package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IExpertRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.ArrayList;
import java.util.List;

public class ExpertInteractor {
    private final IExpertRepository mRepositoryExperts;

    public ExpertInteractor(IExpertRepository repositoryExperts) {
        mRepositoryExperts = repositoryExperts;
    }

    public void loadAllExperts(IExpertsCallBack callBack) {
        mRepositoryExperts.loadAllExperts(callBack);
    }


    public List<Expert> findExpert(List<Expert> expertList, String query) {
        List<Expert> findedExperts = new ArrayList<>();
        String queryLowerCase = query.toLowerCase();
        for (Expert expert : expertList) {
            if (expert.getFirstName().toLowerCase().contains(queryLowerCase) ||
                    expert.getLastName().toLowerCase().contains(queryLowerCase)) {
                findedExperts.add(expert);
            }
        }
        return findedExperts;
    }

    public void loadExpertSchedule(long expertId, IExpertScheduleCallback iExpertScheduleCallback) {
        mRepositoryExperts.loadExpertSchedule(expertId, iExpertScheduleCallback);
    }

    public void updateExpertAppointment(long appointmentId, long clientId, IExpertScheduleCallback iExpertScheduleCallback) {
        mRepositoryExperts.updateExpertAppointment(appointmentId, clientId, iExpertScheduleCallback);
    }

    public void loadExperNameForActiveAppointment(List<Appointment> activeAppointment, List<Long> expertsId, IClientAppointmentCallback dataStatus) {
        mRepositoryExperts.loadExpertsNameForActiveAppointments(activeAppointment, expertsId, dataStatus);
    }
}
