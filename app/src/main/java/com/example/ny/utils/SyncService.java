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

import com.example.ny.R;
import com.example.ny.data.ConverterData;
import com.example.ny.data.NewsResponse;
import com.example.ny.database.AppDatabase;
import com.example.ny.database.NewsEntity;
import com.example.ny.network.INewsEndPoint;
import com.example.ny.network.RestApi;

import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import io.reactivex.schedulers.Schedulers;

public class SyncService extends Service {
    private CompositeDisposable disposables;
    private static final String CHANNEL_ID = "SYNC_SERVICE";
    private static final String ACTION_SNOOZE = "SNOOZE";
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
        snoozeIntent.setAction(ACTION_SNOOZE);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Sync news")
                .setContentText("Now sync news from server")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setProgress(100,0, true)
                .setContentIntent(snoozePendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Stop", snoozePendingIntent)
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
        notificationManager.notify(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(1, syncNotify());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        INewsEndPoint endPoint = RestApi.getInstance().getEndPoint();
        disposables.add(endPoint.getNews()
                .map(NewsResponse::getResults)
                .map(ConverterData::fromListToDatabase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::UpdateDatabase, this::handleError)
        );

        Log.e("test == ", "Service");
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        //}

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

        successNotify();
        stopSelf();

    }

    private void handleError(Throwable throwable) {
        Log.e("Service Error: ", Objects.requireNonNull(throwable.getMessage()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.disposeSafe(disposables);
        disposables = null;
    }
}
