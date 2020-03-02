package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

/**
 * Интерфейс - колбек для загрузки всех существубщих жкспертов
 */
public interface IExpertsCallBack {
    /**
     * @param expertList все существующие эксперты
     */
    void expertsIsLoaded(List<Expert> expertList);

    void messageLoadingExperts(String messageLoadingExperts);
}
