package com.example.pomodoroclock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pomodoroclock.room.AlarmDB
import com.example.pomodoroclock.room.AlarmDao
import com.example.pomodoroclock.room.AlarmRepository
import com.example.pomodoroclock.room.TableInfo


class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private var alarmRepository = AlarmRepository()
    fun AlarmViewModel (application: Application){
        alarmRepository.AlarmRepository(application)
    }
    fun insert(alarm: TableInfo) {
        alarmRepository.insert(alarm)
    }

    fun deleteAll(alarm: TableInfo)
    {
        alarmRepository.deleteAll(alarm)
    }
}

