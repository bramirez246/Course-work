package com.example.pomodoroclock.alarmAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pomodoroclock.R
import com.example.pomodoroclock.room.TableInfo
import kotlinx.android.synthetic.main.alarm_items.view.*


class AlarmRecyclerView: RecyclerView.Adapter<AlarmRecyclerView.AlarmViewHolder2>() {

    private var alarms = emptyList<TableInfo>()

    class AlarmViewHolder2(itemView:View):RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder2 {
        return AlarmViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.alarm_items, parent,false))
    }



    override fun getItemCount(): Int {
        return alarms.size
    }

    override fun onBindViewHolder(holder: AlarmViewHolder2, position: Int) {
        val currentItem = alarms[position]
        holder.itemView.itemAlarmTime.text = String.format("${currentItem.getHour()}: ${currentItem.getMinute()}")
        holder.itemView.itemRecurringDays.text = String.format("${currentItem.getRecurringDaysText()}")
        holder.itemView.itemTitle.text = String.format(currentItem.getTitle())
    }

    fun setAlarms(alarms: List<TableInfo>) {
        this.alarms = alarms
        notifyDataSetChanged()
    }

}