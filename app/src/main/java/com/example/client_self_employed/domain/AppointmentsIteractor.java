package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Expert;

public class AppointmentsIteractor {
    private final IAppointmentsRepository mAppointmentsRepository;

    public AppointmentsIteractor(IAppointmentsRepository appointmentsRepository) {
        mAppointmentsRepository = appointmentsRepository;
        Appointment appointment1 = new Appointment(1, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 1, 10, 30,
                1,
                2);
        Appointment appointment2 = new Appointment(2, "Подкатка", "90 минут", 15000,
                "Европейский", 2020, 2, 1, 12, 0,
                1,
                2);
        Appointment appointment3 = new Appointment(3, "Подкатка", "90 минут", 500,
                "Европейский", 2020, 2, 1, 9, 0,
                1,
                2);

        Appointment appointment4 = new Appointment(4, "Подкатка", "90 минут", 300,
                "Европейский", 2020, 2, 1, 15, 30,
                1,
                2
        );
        Appointment appointment5 = new Appointment(5, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 2, 1, 7, 30,
                1,
                2);
        Appointment appointment6 = new Appointment(6, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 6, 10, 30,
                1,
                2);
        Appointment appointment7 = new Appointment(7, "Подкатка", "90 минут", 15000,
                "Европейский", 2020, 2, 7, 10, 30,
                1,
                2);
        Appointment appointment8 = new Appointment(8, "Подкатка", "90 минут", 500,
                "Европейский", 2020, 2, 8, 10, 30,
                1,
                2);
        Appointment appointment9 = new Appointment(9, "Подкатка", "90 минут", 300,
                "Европейский", 2020, 2, 9, 10, 30,
                1,
                2
        );
        Appointment appointment10 = new Appointment(10, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 2, 1, 19, 0,
                1,
                2);
        Appointment appointment11 = new Appointment(11, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 2, 1, 19, 0,
                2,
                0);
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2);
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 23, "lotvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10);

        /*mAppointmentsRepository.uploadAppointment(appointment1);
        mAppointmentsRepository.uploadAppointment(appointment2);
        mAppointmentsRepository.uploadAppointment(appointment3);
        mAppointmentsRepository.uploadAppointment(appointment4);
        mAppointmentsRepository.uploadAppointment(appointment5);
        mAppointmentsRepository.uploadAppointment(appointment6);
        mAppointmentsRepository.uploadAppointment(appointment7);
        mAppointmentsRepository.uploadAppointment(appointment8);
        mAppointmentsRepository.uploadAppointment(appointment9);
        mAppointmentsRepository.uploadAppointment(appointment10);
        mAppointmentsRepository.uploadAppointment(appointment11);*/
        mAppointmentsRepository.uploadExpert(expert1);
        mAppointmentsRepository.uploadExpert(expert2);
        mAppointmentsRepository.uploadExpert(expert3);


    }

    public void loadClientsAppointments(long clientId, IAppointmentStatus appointmentStatus) {
        mAppointmentsRepository.loadClientsAppointments(clientId, appointmentStatus);
    }

    public void loadClientsExperts(IAppointmentStatus status) {
        mAppointmentsRepository.loadExperts(status);
    }

    public void loadExpert(long expertId, IAppointmentStatus status) {
        // mAppointmentsRepository.loadExpertAppointments(expertId, status);
    }

    public void deleteClientAppointment(long appoinmentId, IAppointmentStatus status) {
        mAppointmentsRepository.deleteClientsAppointment(appoinmentId, status);
    }

    public void uploadAppoinments() {
    }
}
