package com.example.client_self_employed.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.client_self_employed.R;
import com.example.client_self_employed.presentation.HomeClientActivity;
import com.example.client_self_employed.presentation.viewmodels.DetailedAppointmentViewModel;

import java.util.concurrent.TimeUnit;

public class NotificationHandler extends Worker {
    private final Context mContext = getApplicationContext();
    private DetailedAppointmentViewModel mDetailedAppointmentViewModel;
    public static final String CHANNEL_ID = "ID";
    public static final int NOTIFICATION_ID = 1;
    private RemoteViews mNotificationLayout;

    public NotificationHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void schedulerReminder(long duration, Data data, String tag) {

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationHandler.class)
                .setInitialDelay(duration, TimeUnit.SECONDS)
                .addTag(tag)
                .setInputData(data)
                .build();

        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notificationWork);
    }

    public static void cancelReminder(String tag) {
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(tag);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        String title = data.getString(Constants.EXTRA_TITLE);
        String text = data.getString(Constants.EXTRA_TEXT);
        String uri = data.getString(Constants.EXTRA_EXPERT_PHOTO);
        long id = data.getLong(Constants.EXTRA_APPOINTMENT_ID, 1);
        long expertId = data.getLong(Constants.EXPERT_ID, 1);


        createNotification(title, text, id, expertId, null);
        return Result.success();
    }

    private void createNotification(String title, String text, long id, long expertId, String uri) {
        createNotificationChannel();
        mNotificationLayout = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        mNotificationLayout.setTextViewText(R.id.notification_expert_name, title);
        mNotificationLayout.setTextViewText(R.id.notification_time, text);


        Intent intent = new Intent(mContext, HomeClientActivity.class);
        intent.putExtra(Constants.OPEN_DETAILED_FRAGMENT, "update fragment");
        intent.putExtra(Constants.EXTRA_APPOINTMENT_ID, id);
        intent.putExtra(Constants.EXPERT_ID, expertId);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify((int) id, notification.build());

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.notification_channel_name);
            String description = mContext.getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}
