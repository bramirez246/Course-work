package com.example.pomodoroclock

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.pomodoroclock.alarmAdapters.ListOfAlarmsFragment
import com.google.common.base.Stopwatch
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_holder.*

class HolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)
        botNav()
    }

    private fun botNav()
    {
        val clockFragment = ClockFragment()
        val stopwatchFragment = StopWatchFragment()
        val timerFragment = TimerFragment()
        val pomodoroFragment = PomodoroFragment()
        val alarmListFragment = ListOfAlarmsFragment()
        changeFragment(clockFragment)
        bottom_nav.setOnItemSelectedListener{
                item->
            when(item.itemId){
                R.id.ic_clock -> {
                    changeFragment(clockFragment)
                    true
                }
                R.id.ic_stop_watch ->{
                    changeFragment(stopwatchFragment)
                    true
                }
                R.id.ic_timer ->{
                    changeFragment(timerFragment)
                    true
                }
                R.id.ic_Pomodoro->{
                    changeFragment(pomodoroFragment)
                    true
                }
                R.id.ic_AlarmList -> {
                    changeFragment(alarmListFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fContainer,fragment)
                .commit()
        }
    }
}