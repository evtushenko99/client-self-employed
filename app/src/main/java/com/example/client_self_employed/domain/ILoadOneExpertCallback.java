package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Expert;

/**
 * Колбек для  получения информации об одном конкретном эксперте
 * в Fragment Detailed Appointment
 */
public interface ILoadOneExpertCallback {
    void oneExpertIsLoaded(Expert expert);

    void messageLoadOneExpert(String messageLoadOneExpert);
}
