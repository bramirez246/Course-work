package com.example.pomodoroclock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pomodoroclock.room.AlarmDB
import com.example.pomodoroclock.room.AlarmDao
import com.example.pomodoroclock.room.AlarmRepository
import com.example.pomodoroclock.room.TableInfo


class AlarmListViewModel(application: Application) : AndroidViewModel(application) {
    private var alarmRepository = AlarmRepository()
    private lateinit var alarmsLiveData: LiveData<List<TableInfo>>
    fun AlarmListViewModel (application: Application){
        alarmRepository.AlarmRepository(application)
    }
    fun insert(alarm: TableInfo) {
        alarmRepository.insert(alarm)
    }

    fun deleteAll(alarm: TableInfo)
    {
        alarmRepository.deleteAll(alarm)
    }

    fun update(alarm: TableInfo)
    {
        alarmRepository.update(alarm)
    }

    fun getAlarmsLiveData(): LiveData<List<TableInfo>> {
        alarmsLiveData = alarmRepository.getAlarmsLiveData()
        return alarmsLiveData
    }
}

