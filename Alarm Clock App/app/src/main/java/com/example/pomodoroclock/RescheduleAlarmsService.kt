package com.example.pomodoroclock

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider
import com.example.pomodoroclock.room.AlarmRepository
import com.example.pomodoroclock.room.TableInfo


class RescheduleAlarmsService:LifecycleService() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val alarmRepository = AlarmRepository()
        alarmRepository.AlarmRepository(application)
        alarmRepository.getAlarmsLiveData().observe(this, object : Observer<List<TableInfo>> {
            override fun onChanged(alarms: List<TableInfo>?) {
                if (alarms != null) {
                    for (a:TableInfo in alarms) {
                        if (a.isStarted()) {
                            a.schedule(ApplicationProvider.getApplicationContext())
                        }
                    }
                }
            }
        })
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}