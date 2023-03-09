package com.example.pomodoroclock

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.example.pomodoroclock.room.TableInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class RingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        ButterKnife.bind(this)

        var dismiss = findViewById<Button>(R.id.activity_ring_dismiss)
        var snooze = findViewById<Button>(R.id.activity_ring_snooze)
        var imageClock = findViewById<ImageView>(R.id.activity_ring_clock)

        dismiss.setOnClickListener {
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }

        snooze.setOnClickListener { //calendar declarations and calls
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 10)

            //Declare variables and initialize them
            val alarmID: Int = Random().nextInt(Int.MAX_VALUE)
            val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
            val minute: Int = calendar.get(Calendar.MINUTE)
            val started = true
            val recurring = false
            val monday = false
            val tuesday = false
            val wednesday = false
            val thursday = false
            val friday = false
            val saturday = false
            val sunday = false
            val title = "Snooze"
            val created: Long = System.currentTimeMillis()


            val alarm = TableInfo()
            alarm.alarm(
                alarmID,
                hour,
                minute,
                title,
                created,
                started,
                recurring,
                monday,
                tuesday,
                wednesday,
                thursday,
                friday,
                saturday,
                sunday
            )
            alarm.schedule(applicationContext)
            val intentService = Intent(applicationContext, AlarmService::class.java)
            applicationContext.stopService(intentService)
            finish()
        }

        animateClock(imageClock)
    }

    private fun animateClock(imageClock:ImageView) {
        val rotateAnimation = ObjectAnimator.ofFloat(imageClock, "rotation", 0f, 20f, 0f, -20f, 0f)
        rotateAnimation.repeatCount = ValueAnimator.INFINITE
        rotateAnimation.duration = 800
        rotateAnimation.start()
    }

}