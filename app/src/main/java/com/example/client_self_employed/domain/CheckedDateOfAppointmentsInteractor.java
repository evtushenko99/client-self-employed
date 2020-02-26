package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CheckedDateOfAppointmentsInteractor {
    public static List<Appointment> checkData(List<Appointment> expertSchedule) {
        List<Appointment> checkedExpertedSchedule = new ArrayList<>();
        for (Appointment appointment : expertSchedule) {
            Calendar calendar = makeCalendar(appointment);
            if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
                checkedExpertedSchedule.add(appointment);
            }
        }
        return checkedExpertedSchedule;
    }

    public static Calendar makeCalendar(Appointment appointment) {

        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.DAY_OF_MONTH, appointment.getDayOfMonth());
        calendar.set(Calendar.MONTH, appointment.getMonth() - 1);
        calendar.set(Calendar.YEAR, appointment.getYear());
        calendar.set(Calendar.HOUR_OF_DAY, appointment.getStartHourOfDay());
        calendar.set(Calendar.MINUTE, appointment.getStartMinute());

        return calendar;
    }
}
