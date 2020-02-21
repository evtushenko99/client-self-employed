package com.example.client_self_employed.presentation.clicklisteners;

/**
 * Listener для нажатия на элемент списка "Лучших экспертов". Смена цвета и передача
 * id эксперта для последующей записи к нему
 */
public interface BestExpertItemClickListener {
    void onExpertItemClickListener(long expertId);
}
