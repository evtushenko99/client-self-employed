package com.example.client_self_employed.domain;

import androidx.annotation.NonNull;

import com.example.client_self_employed.domain.model.Appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class FilterActiveAppointmentsInteractor {


    public List<Appointment> filterActiveAppointments(long now, @NonNull List<Appointment> expertSchedule) {
        List<Appointment> checkedExpertedSchedule = new ArrayList<>();
        // long now = System.currentTimeMillis();
        for (Appointment appointment : expertSchedule) {
            Calendar calendar = makeCalendar(appointment);
            if (calendar.getTimeInMillis() >= now) {
                checkedExpertedSchedule.add(appointment);
            }
        }
        return checkedExpertedSchedule;
    }

    public Calendar makeCalendar(@NonNull Appointment appointment) {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, appointment.getDayOfMonth());
        calendar.set(Calendar.MONTH, appointment.getMonth() - 1);
        calendar.set(Calendar.YEAR, appointment.getYear());
        calendar.set(Calendar.HOUR_OF_DAY, appointment.getStartHourOfDay());
        calendar.set(Calendar.MINUTE, appointment.getStartMinute());

        return calendar;
    }

}
