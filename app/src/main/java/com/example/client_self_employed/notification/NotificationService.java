package com.example.client_self_employed.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.fragments.FragmentActiveAppointments;

public class NotificationService extends Service {

    public static final String CHANNEL_ID = "ID";
    public static final String ACTION_STOP = "ACTION_CLOSE";
    private RemoteViews mNotificationLayout;
    private String mAppointmentName = "reg r";
    private String mAppointmentTime = "wrgeg";
    private int mNotificationId = 5;

    public NotificationService() {

    }

    public NotificationService(String appointmentName, String appointmentTime, int notificationId) {
        mAppointmentName = appointmentName;
        mAppointmentTime = appointmentTime;
        mNotificationId = notificationId;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationLayout = new RemoteViews(getPackageName(), R.layout.notification);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_STOP:
                    stopSelf();
                    break;
            }
        } else {
            startForeground(mNotificationId, createNotification());
        }
        return START_NOT_STICKY;
    }

    private Notification createNotification() {

        mNotificationLayout = new RemoteViews(getPackageName(), R.layout.notification);
        mNotificationLayout.setTextViewText(R.id.notification_expert_name, mAppointmentName);
        mNotificationLayout.setTextViewText(R.id.notification_time, mAppointmentTime);

        Intent intent = new Intent(this, FragmentActiveAppointments.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mNotificationLayout.setOnClickPendingIntent(mNotificationLayout.getLayoutId(), pendingIntent);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setOnlyAlertOnce(true)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent)
                .setCustomContentView(mNotificationLayout)
                .setSmallIcon(R.mipmap.ic_launcher);


        return notification.build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
