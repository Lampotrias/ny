package com.example.ny.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.ny.data.ConverterData;
import com.example.ny.data.NewsResponse;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.network.INewsEndPoint;
import com.example.ny.network.RestApi;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

public class SyncService extends Service {
    private CompositeDisposable disposables;
    private static final String CHANNEL_ID = "SYNC_SERVICE";
    public static final String ACTION_STOP = "ACTION_STOP";
    @Override
    public void onCreate() {
        super.onCreate();
        disposables = new CompositeDisposable();
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

       // }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification syncNotify(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Intent snoozeIntent = new Intent(this, NetworkReceiver.class);
        snoozeIntent.setAction(ACTION_STOP);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("Sync news")
                .setContentText("Now sync news from server")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setProgress(100,0, true)
                .setContentIntent(snoozePendingIntent)
                .addAction(android.R.drawable.btn_star, "Stop", snoozePendingIntent)
                .build();
    }
    private void successNotify(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("Sync news")
                        .setContentText("Finish");

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(2, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test == ", "Service start");
        startForeground(1, syncNotify());

        INewsEndPoint endPoint = RestApi.getInstance().getEndPoint();
        disposables.add(endPoint.getNews()
                .map(NewsResponse::getResults)
                .map(ConverterData::fromListToDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(20, TimeUnit.SECONDS)
                .subscribe(this::UpdateDatabase, this::handleError)
        );

        Log.e("test == ", "Service end");
        return START_STICKY;
    }

    private void UpdateDatabase(List<NewsEntity> newsEntities) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        disposables.add(db.newsDao().deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

        disposables.add(db.newsDao().insertAll(newsEntities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
        stopForeground(true);
        Log.e("test == ", "Service stopForeground");
        successNotify();
        stopSelf();
    }

    private void handleError(Throwable throwable) {
        Log.e("Service Error: ", Objects.requireNonNull(throwable.getMessage()));
    }

    @Override
    public void onDestroy() {
        Log.e("test == ", "Service onDestroy");
        super.onDestroy();
        Utils.disposeSafe(disposables);
        disposables = null;
    }
}
