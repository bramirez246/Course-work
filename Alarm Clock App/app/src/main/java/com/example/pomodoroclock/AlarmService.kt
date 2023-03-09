package com.example.pomodoroclock

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.os.Vibrator
import android.widget.Toast


class AlarmService:Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var vibrator: Vibrator? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm)
        mediaPlayer!!.isLooping = true
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //this was stuff for notifications cause the issues with the app crashing
        //notification code is not working properly due to intent switching i believe.

//        val alarmBroadcastReceiver = AlarmBroadcastReceiver()
//        val app = App()
//        val notificationIntent = Intent(this, RingActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
//        val alarmTitle = String.format("%s Alarm", intent.getStringExtra(alarmBroadcastReceiver.Title))
//        val notification: Notification = NotificationCompat.Builder(this, app.CHANNEL_ID)
//            .setContentTitle(alarmTitle)
//            .setContentText("Ring Ring .. Ring Ring")
//            .setSmallIcon(R.drawable.ic_baseline_alarm_24)
//            .setContentIntent(pendingIntent)
//            .build()
        mediaPlayer!!.start()

        val pattern = longArrayOf(0, 100, 1000)
        vibrator!!.vibrate(pattern, 0)
//        startForeground(1, notification)
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        vibrator!!.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

}