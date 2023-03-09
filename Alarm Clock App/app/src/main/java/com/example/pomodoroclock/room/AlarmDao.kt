package com.example.pomodoroclock.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlarmDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(alarm: TableInfo)

    @Query("DELETE FROM Alarm")
    fun deleteAll()

    @Query("SELECT * FROM Alarm ORDER BY createdAlarm ASC")
    fun getAlarms(): LiveData<List<TableInfo>>

    @Update
    fun update(alarm: TableInfo)
}