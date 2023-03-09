package com.example.pomodoroclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import java.util.*


class AlarmBroadcastReceiver: BroadcastReceiver() {
    val Monday:String = "MONDAY"
    val Tuesday:String = "TUESDAY"
    val Wednesday:String = "WEDNESDAY"
    val Thursday:String = "THURSDAY"
    val Friday:String = "FRIDAY"
    val Saturday:String = "SATURDAY"
    val Sunday:String = "SUNDAY"
    val Recurring:String = "RECURRING"
    val Title:String = "TITLE"

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        } else {
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if (!intent.getBooleanExtra(Recurring, false)) {
                startAlarmService(context, intent)
            }
            else
            {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent)
                }
            }
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        intentService.putExtra(Title, intent.getStringExtra(Title))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today: Int = calendar.get(Calendar.DAY_OF_WEEK)
        if(today == Calendar.MONDAY)
        {
            if(intent.getBooleanExtra(Monday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.TUESDAY)
        {
            if(intent.getBooleanExtra(Tuesday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.WEDNESDAY)
        {
            if(intent.getBooleanExtra(Wednesday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.THURSDAY)
        {
            if(intent.getBooleanExtra(Thursday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.FRIDAY)
        {
            if(intent.getBooleanExtra(Friday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.SATURDAY)
        {
            if(intent.getBooleanExtra(Saturday, false))
            {
                return true
            }
            return false
        }
        else if(today == Calendar.SUNDAY)
        {
            if(intent.getBooleanExtra(Sunday, false))
            {
                return true
            }
            return false
        }
        else
        {
            return false
        }
    }
}