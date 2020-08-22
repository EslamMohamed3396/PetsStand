package com.proatcoding.pets.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.proatcoding.pets.R;
import com.proatcoding.pets.activity.BaseActivity;
import com.proatcoding.pets.models.AlarmDTO;

public class AlarmReceiver extends BroadcastReceiver {
    public static PendingIntent pendingIntent;
    public static AlarmManager alarmManager;

    static Ringtone ringtone;
    NotificationManager mNotificationManager;
    String msg ="";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("TAG", "onRecieve()---------- ");

        if(intent.hasExtra("alarm")){
            msg = intent.getStringExtra("alarm");
        }

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        sendNotification(context, msg);

        /*PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";

        Uri defaultSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Consts.PET_CARE)
                .setSound(defaultSoundUri)
                *//* .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))*//*
                .setContentText(context.getResources().getString(R.string.app_name)).setAutoCancel(true).setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());*/
    }

    public static void setReminderAlarm(Context context, AlarmDTO alarm){
        Intent myIntent = new Intent(context, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getDatetime(), pendingIntent);
    }

    public void cancelReminder(){
        if(alarmManager!=null){
            alarmManager.cancel(pendingIntent);
            ringtone.stop();
        }
    }


    private void sendNotification(Context context, String msg) {
        Intent intent = new Intent(context, BaseActivity.class); // Here pass your activity where you want to redirect.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, (int) (Math.random() * 100), intent, PendingIntent.FLAG_ONE_SHOT);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
            currentapiVersion = R.mipmap.ic_launcher;
        } else{
            currentapiVersion = R.mipmap.ic_launcher;
        }

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(currentapiVersion)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setSound(defaultSoundUri)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
        long notificationId = System.currentTimeMillis();

        mNotificationManager.notify((int) notificationId, notificationBuilder.build());
    }
}
