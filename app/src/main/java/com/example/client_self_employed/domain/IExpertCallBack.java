package com.example.client_self_employed.domain;

import androidx.annotation.Nullable;

import com.example.client_self_employed.domain.model.Expert;

import java.util.List;

/**
 * Интерфейс для работы с поиском экспертов в отдельном фрагменте
 */
public interface IExpertCallBack {
    /**
     * @param expertList все существующие эксперты
     */
    void expertsIsLoaded(@Nullable List<Expert> expertList);
}
