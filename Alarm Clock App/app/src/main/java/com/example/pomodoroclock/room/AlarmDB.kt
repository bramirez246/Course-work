package com.example.pomodoroclock.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [TableInfo::class], version = 1, exportSchema = false)
abstract class AlarmDB:RoomDatabase() {
    abstract fun alarmDao(): AlarmDao?
    companion object {
        @Volatile
        private var INSTANCE: AlarmDB? = null
        private val NUMBER_OF_THREADS = 4
        var databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): AlarmDB? {
            if (INSTANCE == null) {
                synchronized(AlarmDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AlarmDB::class.java,
                            "alarm_db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}