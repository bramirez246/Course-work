package com.example.pomodoroclock.room

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pomodoroclock.AlarmBroadcastReceiver
import com.example.pomodoroclock.AlarmService
import com.example.pomodoroclock.DayUtil
import java.util.*


@Entity(tableName = "Alarm")
class TableInfo {
    //Declare variables and initialize them
    //Specify primary key
    @PrimaryKey(autoGenerate = true)
    private var alarmID:Int = 0
    @ColumnInfo(name = "hour")
    private var hour:Int = 0
    @ColumnInfo(name = "minutes")
    private  var minute:Int = 0
    @ColumnInfo(name = "startAlarm")
    private var started:Boolean = false
    @ColumnInfo(name = "recurringAlarm")
    private  var recurring:Boolean = false
    @ColumnInfo(name = "Days")
    private var monday:Boolean = false
    private  var tuesday:Boolean = false
    private  var wednesday:Boolean = false
    private  var thursday:Boolean = false
    private  var friday:Boolean = false
    private var saturday:Boolean = false
    private  var sunday:Boolean = false
    @ColumnInfo(name = "Title")
    private var title: String = ""
    @ColumnInfo(name = "createdAlarm")
    private var created: Long = 0


    //function for alarm and initializes alarm values and days of the week
    fun alarm(alarmID: Int, hour: Int, minute: Int, title: String, created: Long, started: Boolean,
              recurring: Boolean, monday: Boolean, tuesday: Boolean, wednesday: Boolean, thursday: Boolean,
              friday: Boolean, saturday: Boolean, sunday: Boolean)
    {
        this.alarmID = alarmID
        this.hour = hour
        this.minute = minute
        this.started = started
        this.recurring = recurring
        this.monday = monday
        this.tuesday = tuesday
        this.wednesday = wednesday
        this.thursday = thursday
        this.friday = friday
        this.saturday = saturday
        this.sunday = sunday
        this.title = title
        this.created = created
    }

    //declare setters and getter aka modifiers and accessors
    fun getHour(): Int {
        return hour
    }
    fun setHour(hour: Int)
    {
        this.hour = hour
    }

    fun getMinute(): Int {
        return minute
    }
    fun setMinute(minute: Int)
    {
        this.minute = minute
    }
    fun isStarted(): Boolean {
        return started
    }
    fun setStarted(started: Boolean) {
        this.started = started
    }

    fun getAlarmID(): Int {
        return alarmID
    }

    fun setAlarmID(alarmId: Int) {
        this.alarmID = alarmId
    }

    fun isRecurring(): Boolean {
        return recurring
    }
    fun setRecurring(recurring: Boolean)
    {
        this.recurring = recurring
    }

    fun isMonday(): Boolean {
        return monday
    }

    fun setMonday(monday: Boolean)
    {
        this.monday = monday
    }

    fun isTuesday(): Boolean {
        return tuesday
    }

    fun setTuesday(tuesday: Boolean)
    {
        this.tuesday = tuesday
    }

    fun isWednesday(): Boolean {
        return wednesday
    }

    fun setWednesday(wednesday: Boolean)
    {
        this.wednesday = wednesday
    }

    fun isThursday(): Boolean {
        return thursday
    }

    fun setThursday(thursday: Boolean)
    {
        this.thursday = thursday
    }

    fun isFriday(): Boolean {
        return friday
    }

    fun setFriday(friday: Boolean)
    {
        this.friday = friday
    }

    fun isSaturday(): Boolean {
        return saturday
    }

    fun setSaturday(saturday: Boolean)
    {
        this.saturday = saturday
    }

    fun isSunday(): Boolean {
        return sunday
    }

    fun setSunday(sunday: Boolean)
    {
        this.sunday = sunday
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String)
    {
        this.title =title
    }

    fun getCreated(): Long {
        return created
    }

    fun setCreated(created: Long) {
        this.created = created
    }


    //schedule function will put the values assigned by alarms function into Alarm Broadcast class
    fun schedule(context: Context?)
    {
        //alarmManager is in charge of getting the context of Alarm Service and treating it as Alarm manager
        //AlarmManager allows us to perform time based operations outside of the lifetime of our application
        //Which is what we want for example waking someone up at a scheduled alarm even if the app is closed
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmString = AlarmBroadcastReceiver()
        val alarmService = AlarmService()

        intent.putExtra(alarmString.Recurring, recurring)
        intent.putExtra(alarmString.Monday, monday)
        intent.putExtra(alarmString.Tuesday, tuesday)
        intent.putExtra(alarmString.Wednesday, wednesday)
        intent.putExtra(alarmString.Thursday, thursday)
        intent.putExtra(alarmString.Friday, friday)
        intent.putExtra(alarmString.Saturday, saturday)
        intent.putExtra(alarmString.Sunday, sunday)
        intent.putExtra(alarmString.Title, title)

        //so Pending intent allows us to wrap the intent for future uses for example an the wrapped intent
        // will go off everytime an alarm goes off or the user taps on a notification
        //context is the current instruction and details going on form whatever we passed.
        //alarmID is the specific id we are giving to the sender
        //intent is what we are intending to broadcast in this case we want to broadcast all the days
        //Titles and recurrences
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmID, intent, 0)

        //calendar allows to get specific dates of the calendar and manipulate the times
        //As well as set specific times in certain date fields in the calendar using Hour_of_day
        val calendar: Calendar = Calendar.getInstance()
        //using the system we can get the current time in milliseconds
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)//we fill the current day of hour with the inserted hour field
        calendar.set(Calendar.MINUTE, minute)//we fill the minutes in calendar with minute input
        calendar.set(Calendar.SECOND, 0)//we fill calendar seconds field with seconds input
        calendar.set(Calendar.MILLISECOND, 0)//we fill calendar milliseconds with milliseconds input

        //if alarm has been triggered increment to next day by adding 1
        if(calendar.timeInMillis <= System.currentTimeMillis())
        {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
        }

        //recurring is not true then its a one time alarm enter if
        if(!recurring)
        {
            //toast text field
            val toastText:String = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmID);


            //else display toast message
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()

            //
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,//wakes up device if alarm goes off
                calendar.timeInMillis,//calendar has the date/time of when the device should go off
                alarmPendingIntent//we are saying initiate intent to go off aka alarm for us
            )
        }
        else
        {
            val toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmID);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            //24hrs in a day. 60 minutes in a hour and 60 seconds in a minute finally convert to milliseconds by multiplying by 1000
            val runAllDay:Long = 24 * 60 * 60 * 1000
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                runAllDay,
                alarmPendingIntent
            )
        }
        this.started = true
    }


    //this function returns the
    fun getRecurringDaysText(): String? {
        //if recurring is not true then return null
        if (!recurring) {
            return null
        }

        var days = ""
        if (monday) {
            days += "Monday "
        }
        if (tuesday) {
            days += "Tuesday "
        }
        if (wednesday) {
            days += "Wednesday "
        }
        if (thursday) {
            days += "Thursday "
        }
        if (friday) {
            days += "Friday "
        }
        if (saturday) {
            days += "Saturday "
        }
        if (sunday) {
            days += "Sunday "
        }

        return days
    }

    fun cancelAlarm(context: Context) {
        var hour = getHour()
        var minute = getMinute()
        var alarmID = getAlarmID()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, alarmID, intent, 0)
        alarmManager.cancel(alarmPendingIntent)
        this.started = false
        val toastText = String.format("Alarm cancelled for $hour : $minute with id $alarmID")
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }


}