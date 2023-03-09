package com.example.pomodoroclock.alarmAdapters

import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoroclock.R
import com.example.pomodoroclock.room.TableInfo
import java.lang.String


class AlarmViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    private var alarmTime: TextView? = null
    private var alarmRecurring: ImageView? = null
    private var alarmRecurringDays: TextView? = null
    private var alarmTitle: TextView? = null

    var alarmStarted: Switch? = null

    private var listener: OnToggleAlarm? = null

    fun AlarmViewHolder(itemView: View, listener:OnToggleAlarm) {
        alarmTime = itemView.findViewById(R.id.itemAlarmTime)
        alarmRecurring = itemView.findViewById(R.id.itemRecurring)
        alarmRecurringDays = itemView.findViewById(R.id.itemRecurringDays)
        alarmTitle = itemView.findViewById(R.id.itemTitle)
        alarmStarted = itemView.findViewById(R.id.itemSwitch)
        this.listener = listener
    }

    fun bind(alarm:TableInfo)
    {
        val alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute())

        alarmTime!!.text = alarmText
        alarmStarted!!.isChecked = alarm.isStarted()

        if (alarm.isRecurring()) {
            alarmRecurring!!.setImageResource(R.drawable.ic_repeat)
            alarmRecurringDays!!.text = alarm.getRecurringDaysText()
        } else {
            alarmRecurring!!.setImageResource(R.drawable.ic_looks_one)
            alarmRecurringDays!!.text = String.format("Once Off")
        }

        if (alarm.getTitle().isNotEmpty()) {
            alarmTitle!!.text = String.format("${alarm.getTitle()} | ${alarm.getAlarmID()} | ${alarm.getCreated()}")
        } else {
            alarmTitle!!.text = String.format("${alarm.getAlarmID()} | Alarm | ${alarm.getCreated()}")
        }
        alarmStarted!!.setOnCheckedChangeListener { buttonView, isChecked ->
            listener!!.onToggle(alarm)
        }
    }

}
