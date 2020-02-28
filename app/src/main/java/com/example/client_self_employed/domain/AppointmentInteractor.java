package com.example.client_self_employed.domain;

import com.example.client_self_employed.data.IAppointmentRepository;
import com.example.client_self_employed.domain.model.Appointment;
import com.example.client_self_employed.domain.model.Client;
import com.example.client_self_employed.domain.model.Expert;

public class AppointmentInteractor {
    private final IAppointmentRepository mAppointmentsRepository;


    public AppointmentInteractor(IAppointmentRepository appointmentsRepository) {
        mAppointmentsRepository = appointmentsRepository;

        Appointment appointment1 = new Appointment(1, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 28, 10, 30,
                1,
                0, 0);
        Appointment appointment2 = new Appointment(2, "Подкатка", "90 минут", 15000,
                "Европейский", 2020, 2, 28, 12, 0,
                1,
                0, 0);
        Appointment appointment3 = new Appointment(3, "Подкатка", "90 минут", 500,
                "Европейский", 2020, 3, 1, 9, 0,
                3,
                0, 0);

        Appointment appointment4 = new Appointment(4, "Подкатка", "90 минут", 300,
                "Европейский", 2020, 3, 1, 15, 30,
                2,
                0, 0
        );
        Appointment appointment5 = new Appointment(5, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 3, 1, 7, 30,
                2,
                0, 0);
        Appointment appointment6 = new Appointment(6, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 3, 6, 10, 30,
                1,
                0, 0);
        Appointment appointment7 = new Appointment(7, "Подкатка", "90 минут", 15000,
                "Европейский", 2020, 3, 7, 10, 30,
                3,
                0, 0);
        Appointment appointment8 = new Appointment(8, "Подкатка", "90 минут", 500,
                "Европейский", 2020, 3, 8, 10, 30,
                1,
                0, 0);
        Appointment appointment9 = new Appointment(9, "Подкатка", "90 минут", 300,
                "Европейский", 2020, 3, 9, 10, 30,
                1,
                0, 0
        );
        Appointment appointment10 = new Appointment(10, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 3, 1, 19, 0,
                1,
                0, 0);
        Appointment appointment11 = new Appointment(11, "Тренировка", "90 минут", 5500,
                "Остров", 2020, 2, 26, 17, 19,
                1,
                0,
                0);
        Expert expert1 = new Expert(1, "Евтушенко", "Максим", "Евгеньевич", 21, "evtushenko99@mail.ru", "+7-906-087-11-00", "Тренер по фигурному катанию", 5, null);
        Expert expert2 = new Expert(2, "Юрина", "Марина", "Игоревна", 23, "marina_yri@mail.ru", "+7-915-133-97-43", "Инструктор по зумбе", 2, null);
        Expert expert3 = new Expert(3, "Литвиненко", "Сергей", "Владиславович", 40, "litvinenko@mail.ru", "+7-915-133-97-43", "Тренер по йоге", 10, null);
        Client client = new Client(2, "Юрин", "Максим", "Евгеньевич", 21, 22, 2, 1999, "evtushenko99@mail.ru", "+7-906-087-11-00", "Мужской", null);

      /*  mAppointmentsRepository.uploadAppointment(appointment1);
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
        mAppointmentsRepository.uploadClient(client);
*/
    }


    public void loadClientsAppointments(long clientId, IAppointmentsCallback appointmentsCallback) {
        mAppointmentsRepository.loadClientActiveAppointments(clientId, appointmentsCallback);
    }

    public void deleteClientAppointment(long appoinmtentId, IClientAppointmentCallback status) {
        mAppointmentsRepository.deleteClientsAppointment(appoinmtentId, status);
    }


}
