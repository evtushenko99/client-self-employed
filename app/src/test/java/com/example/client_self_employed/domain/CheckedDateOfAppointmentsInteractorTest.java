package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Appointment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CheckedDateOfAppointmentsInteractorTest {
    private FilterActiveAppointmentsInteractor mInteractor;
    private long mNow = 1582761600000L; // 28.02.2020

    @Before
    public void setUp() {
        mInteractor = new FilterActiveAppointmentsInteractor();
    }

    @Test
    public void filterActiveAppointments() {
        //arrange
        List<Appointment> source = createSourceAppointments();
        List<Appointment> expected = createExpectedAppointments();

        //act
        List<Appointment> actual = mInteractor.filterActiveAppointments(mNow, source);

        //assert
        assertThat(actual, is(expected));
    }

    @Test
    public void makeCalendar() {
        //arrange
        Appointment appointment = new Appointment(1, "Подкатка", "90 минут", 2000,
                "Европейский", 2020, 2, 28, 10, 30,
                1,
                0, 0);
        Calendar expected = makeCalendar(appointment);
        //act
        Calendar source = mInteractor.makeCalendar(appointment);
        //assert
        assertThat(source, is(expected));
    }

    private List<Appointment> createSourceAppointments() {
        List<Appointment> actual = new ArrayList<>(createExpectedAppointments());
        for (int i = 12; i < 21; i++) {
            actual.add(new Appointment(i, "Подкатка", "90 минут", 2000,
                    "Европейский", 2020, 2, i, 10, 30,
                    1,
                    0, 0));
        }
        return actual;
    }

    private List<Appointment> createExpectedAppointments() {
        List<Appointment> expected = new ArrayList<>();
        for (int i = 1; i < 12; i++) {
            expected.add(new Appointment(i, "Подкатка", "90 минут", 2000,
                    "Европейский", 2020, 3, i, 10, 30,
                    1,
                    0, 0));
        }
        return expected;

    }

    private Calendar makeCalendar(Appointment appointment) {

        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.DAY_OF_MONTH, appointment.getDayOfMonth());
        calendar.set(Calendar.MONTH, appointment.getMonth() - 1);
        calendar.set(Calendar.YEAR, appointment.getYear());
        calendar.set(Calendar.HOUR_OF_DAY, appointment.getStartHourOfDay());
        calendar.set(Calendar.MINUTE, appointment.getStartMinute());

        return calendar;
    }

}