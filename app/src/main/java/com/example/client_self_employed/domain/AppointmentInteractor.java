package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;

public class AppointmentInteractor {
    private final IAppointmentRepository mAppointmentsRepository;


    public AppointmentInteractor(IAppointmentRepository appointmentsRepository) {
        mAppointmentsRepository = appointmentsRepository;

        Appointment appointment1 = new Appointment(1, "Подкатка", "90 минут", 1500,
                "Европейский", 2020, 3, 2, 19, 0,
                1,
                0, 0);
        Appointment appointment2 = new Appointment(2, "Подкатка", "90 минут", 1500,
                "Европейский", 2020, 3, 2, 20, 30,
                1,
                0, 0);
        Appointment appointment3 = new Appointment(3, "Подкатка", "90 минут", 1500,
                "Европейский", 2020, 3, 2, 14, 30,
                1,
                0, 0);

        Appointment appointment4 = new Appointment(4, "Подкатка", "90 минут", 1500,
                "Европейский", 2020, 3, 7, 15, 30,
                1,
                0, 0
        );
        Appointment appointment5 = new Appointment(5, "Индивидуальное занятие", "60 минут", 2500,
                "Остров", 2020, 3, 5, 7, 30,
                2,
                0, 0);
        Appointment appointment6 = new Appointment(6, "Индивидуальное занятие", "60 минут", 2500,
                "Европейский", 2020, 3, 6, 10, 30,
                2,
                0, 0);
        Appointment appointment7 = new Appointment(7, "Индивидуальное занятие", "90 минут", 3000,
                "Европейский", 2020, 3, 7, 16, 30,
                2,
                0, 0);
        Appointment appointment8 = new Appointment(8, "Урок по информатике", "90 минут", 2000,
                "Европейский", 2020, 3, 8, 10, 30,
                3,
                0, 0);
        Appointment appointment9 = new Appointment(9, "Урок по информатике", "60 минут", 1500,
                "Европейский", 2020, 3, 9, 19, 0,
                3,
                0, 0
        );
        Appointment appointment10 = new Appointment(10, "Вождение", "90 минут", 1200,
                "Остров", 2020, 3, 10, 19, 0,
                4,
                0, 0);
        Appointment appointment11 = new Appointment(11, "Окрашивание бровей", "30 минут", 1000,
                "Остров", 2020, 3, 10, 17, 19,
                5,
                0,
                0);
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 35, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 10, null);
        expert1.setReviewCount(10);
        expert1.setTotalRating(50);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 5, null);
        expert2.setReviewCount(9);
        expert2.setTotalRating(38);
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Преподаватель по информатике", 10, null);
        expert3.setReviewCount(4);
        expert3.setTotalRating(18);
        Expert expert4 = new Expert(4, "Качалов", "Олег", "Викторович", 25, "litvinenko@mail.ru", "+7-915-133-97-43", "Инструктор по вождению", 15, null);
        expert4.setReviewCount(2);
        expert4.setTotalRating(6);
        Expert expert5 = new Expert(5, "Евтушенко", "Анна", "Евгеньевна", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Маникюрщица", 7, null);
        expert5.setReviewCount(6);
        expert5.setTotalRating(15);
        Expert expert6 = new Expert(6, "Пучкова", "Юлия", "Сергеевна", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Бровистка", 9, null);
        expert6.setReviewCount(2);
        expert6.setTotalRating(4);
        Client client = new Client(2, "Юрин", "Максим", "Евгеньевич", 21, 22, 2, 1999, "evtushenko99@mail.ru", "+7-906-087-11-00", "Мужской", null);

     /*    mAppointmentsRepository.uploadClient(client);
        mAppointmentsRepository.uploadAppointment(appointment1);
        mAppointmentsRepository.uploadAppointment(appointment2);
        mAppointmentsRepository.uploadAppointment(appointment3);
        mAppointmentsRepository.uploadAppointment(appointment4);
        mAppointmentsRepository.uploadAppointment(appointment5);
        mAppointmentsRepository.uploadAppointment(appointment6);
        mAppointmentsRepository.uploadAppointment(appointment7);
        mAppointmentsRepository.uploadAppointment(appointment8);
        mAppointmentsRepository.uploadAppointment(appointment9);
        mAppointmentsRepository.uploadAppointment(appointment10);
        mAppointmentsRepository.uploadAppointment(appointment11);

       mAppointmentsRepository.uploadExpert(expert1);
        mAppointmentsRepository.uploadExpert(expert2);
        mAppointmentsRepository.uploadExpert(expert3);
        mAppointmentsRepository.uploadExpert(expert4);
        mAppointmentsRepository.uploadExpert(expert5);
        mAppointmentsRepository.uploadExpert(expert6);*/


    }


    public void loadClientsAppointments(long clientId, IAppointmentsCallback appointmentsCallback) {
        mAppointmentsRepository.loadClientActiveAppointments(clientId, appointmentsCallback);
    }

    public void deleteClientAppointment(long appoinmtentId, IClientAppointmentCallback status) {
        mAppointmentsRepository.deleteClientsAppointment(appoinmtentId, status);
    }


}
