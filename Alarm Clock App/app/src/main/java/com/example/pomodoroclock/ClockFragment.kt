package com.example.pomodoroclock

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import butterknife.ButterKnife
import com.example.pomodoroclock.room.TableInfo
import java.util.*

class ClockFragment:Fragment() {
    private lateinit var alarmViewModel:AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
    }

    //creates/initializes the view but we must return a view or null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clock, container, false)


        return view
    }

    //    3rd Event that loads up makes sure view is fully loaded
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recurring = view.findViewById<CheckBox>(R.id.fragment_createalarm_recurring)
        val recurringOptions = view.findViewById<LinearLayout>(R.id.fragment_createalarm_recurring_options)
        val scheduleAlarm = view.findViewById<Button>(R.id.fragment_createalarm_scheduleAlarm)
        val alarmOption = view.findViewById<Button>(R.id.fragment_alarmoptions)
        ButterKnife.bind(this, view);

        recurring.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView:CompoundButton, isChecked:Boolean) {
                if (isChecked) {
                    recurringOptions.visibility = View.VISIBLE;
                } else {
                    recurringOptions.visibility = View.GONE;
                }
            }
        })

        scheduleAlarm.setOnClickListener {
            scheduleAlarm(view)
            requireActivity().startService(Intent(activity, AlarmService::class.java))
        }

        alarmOption.setOnClickListener {
            val i = Intent(activity,RingActivity::class.java)
            startActivity(i)
        }
    }



    private fun scheduleAlarm(view: View) {
        var timePicker = view.findViewById<TimePicker>(R.id.fragment_createalarm_timePicker)
        var title = view.findViewById<EditText>(R.id.fragment_createalarm_title)
        var recurring = view.findViewById<CheckBox>(R.id.fragment_createalarm_recurring)
        var monday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkMon)
        var tuesday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkTue)
        var wednesday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkWed)
        var thursday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkThu)
        var friday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkFri)
        var saturday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkSat)
        var sunday = view.findViewById<CheckBox>(R.id.fragment_createalarm_checkSun)


        var tPicker = TimePicker()
        val alarmID: Int = Random().nextInt(Int.MAX_VALUE)

        var tableInfo = TableInfo()
        tableInfo.alarm(
            alarmID, tPicker.getTimePickerHour(timePicker), tPicker.getTimePickerMinute(timePicker),
            title.text.toString(), System.currentTimeMillis(),
            true, recurring.isChecked, monday.isChecked, tuesday.isChecked, wednesday.isChecked,
            thursday.isChecked, friday.isChecked, saturday.isChecked, sunday.isChecked
        )
        alarmViewModel.AlarmViewModel(requireActivity().application)
        alarmViewModel.insert(tableInfo)
        tableInfo.schedule(context)
    }
}