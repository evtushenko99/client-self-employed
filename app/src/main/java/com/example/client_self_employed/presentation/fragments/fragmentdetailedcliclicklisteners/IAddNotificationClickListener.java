package com.example.client_self_employed.presentation.fragments.fragmentdetailedcliclicklisteners;

public interface IAddNotificationClickListener {
    /**
     * Контракт, отвечающий за добавление уведомления для активной записи
     *
     * @param serviceName   - наименование услуги
     * @param startTime     - время начала услуги
     * @param appointmentId - id записи
     */
    void onAddNotificationClickListener(String serviceName, String startTime, long appointmentId);
}
