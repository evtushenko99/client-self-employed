package com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners;

public interface IAddNotificationClickListener {
    /**
     * Контракт, отвечающий за добавление уведомления для активной записи
     *
     */
    void onAddNotificationClickListener();

    /**
     * КОнткракт, отвечающий за удаление уведомления для активной записи
     *
     * @param appointmentId - id активной записи
     */
    void onRemoveNotificationClickListenrs(long appointmentId);
}
