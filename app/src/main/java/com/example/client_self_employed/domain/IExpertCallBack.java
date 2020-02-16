package com.example.client_self_employed.domain;

import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

/**
 * Интерфейс для работы с поиском экспертов в отдельном фрагменте
 */
public interface IExpertCallBack {
    /**
     * @param expertList все существующие эксперты
     */
    void expertsIsLoaded(List<Expert> expertList);
}
