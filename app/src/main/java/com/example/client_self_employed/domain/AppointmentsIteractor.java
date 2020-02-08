package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

import java.util.Date;

public class AppointmentsIteractor {
    private final IAppointmentsRepository mAppointmentsRepository;

    public AppointmentsIteractor(IAppointmentsRepository appointmentsRepository) {
        mAppointmentsRepository = appointmentsRepository;
        Appointment appointment = new Appointment(1, "Подкатка", "12:30", "90 минут", 2000,
                "Европейский", new Date(),
                1,
                2);
        Appointment appointment1 = new Appointment(2, "Подкатка", "14:00", "90 минут", 15000,
                "Европейский", new Date(),
                2,
                 1);
        Appointment appointment2 = new Appointment(3, "Подкатка", "15:30", "90 минут", 500,
                "Европейский", new Date(),
                1,
             1);
        Appointment appointment3 = new Appointment(4, "Подкатка", "17:00", "90 минут", 300,
                "Европейский", new Date(),
                1,
                2
                );
        Appointment appointment4 = new Appointment(4, "Тренировка", "18:30", "90 минут", 5500,
                "Остров", new Date(),
                3,
                2);
        Expert expert1 = new Expert(1, "Евтушенко М.Е.", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5);
        Expert expert2 = new Expert(2, "Юрина М.И,", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2);
        Expert expert3 = new Expert(3, "Литвиненко С.В,,", 23, "lotvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10);
        /*mAppointmentsRepository.uploadAppointment(appointment);
        mAppointmentsRepository.uploadAppointment(appointment1);
        mAppointmentsRepository.uploadAppointment(appointment2);
        mAppointmentsRepository.uploadAppointment(appointment3);
        mAppointmentsRepository.uploadAppointment(appointment4);
        mAppointmentsRepository.uploadExpert(expert1);
        mAppointmentsRepository.uploadExpert(expert2);
        mAppointmentsRepository.uploadExpert(expert3);*/


    }

    public void loadClientsAppointments(long clientId, IAppointmentStatus appointmentStatus) {
        mAppointmentsRepository.loadClientsAppointments(clientId, appointmentStatus);
    }

    public void loadClientsExperts(IAppointmentStatus status){
        mAppointmentsRepository.loadExperts(status);
    }
    public void loadExpert(long expertId, IAppointmentStatus status) {
        // mAppointmentsRepository.loadExpertAppointments(expertId, status);
    }
public void deleteClientAppointment(long appoinmentId, IAppointmentStatus status){
        mAppointmentsRepository.deleteClientsAppointment(appoinmentId, status);
}

    public void uploadAppoinments() {
    }
}
