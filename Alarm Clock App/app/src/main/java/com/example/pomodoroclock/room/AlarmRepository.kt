package com.example.pomodoroclock.room

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.pomodoroclock.TimePicker

class AlarmRepository {
    private lateinit var alarmDao:AlarmDao
    private lateinit var alarmsLiveData: LiveData<List<TableInfo>>
    fun AlarmRepository(application: Application) {
        val db: AlarmDB? = AlarmDB.getDatabase(application)
        if (db != null) {
            alarmDao = db.alarmDao()!!
        }
        alarmsLiveData = alarmDao.getAlarms()
    }

    fun insert(alarm: TableInfo?) {
        AlarmDB.databaseWriteExecutor.execute {
            if (alarm != null) {
                alarmDao.insert(alarm)
            }
        }
    }

    fun update(alarm: TableInfo?) {
        AlarmDB.databaseWriteExecutor.execute {
            if (alarm != null) {
                alarmDao.update(alarm)
            }
        }
    }

    fun deleteAll(alarm: TableInfo?)
    {
        AlarmDB.databaseWriteExecutor.execute {
            if (alarm != null) {
                alarmDao.deleteAll()
            }
        }
    }

    fun getAlarmsLiveData(): LiveData<List<TableInfo>> {
        return alarmsLiveData
    }
}