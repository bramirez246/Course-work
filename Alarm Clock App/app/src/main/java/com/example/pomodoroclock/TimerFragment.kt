package com.example.pomodoroclock

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*


class TimerFragment : Fragment(R.layout.fragment_timer) {
    companion object{
        lateinit var countDownTimer: CountDownTimer
    }

    private lateinit var editTextInput: EditText
    private lateinit var textViewCountDown: TextView
    private lateinit var btnSet: Button
    private lateinit var btnStartPause: Button
    private lateinit var btnReset: Button
    private lateinit var btnClear: Button

    private var timerRunning = false

    private var startTimeInMillis: Long = 0
    private var timeLeftInMillis: Long = 0
    private var endTime: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextInput = view.findViewById(R.id.edit_text_input)
        textViewCountDown = view.findViewById(R.id.text_view_countdown)

        btnSet = view.findViewById(R.id.button_set)
        btnStartPause = view.findViewById(R.id.button_start_pause)
        btnReset = view.findViewById(R.id.button_reset)
        btnClear = view.findViewById(R.id.button_clear)

        btnSet.setOnClickListener {
            val input: String = editTextInput.text.toString()
            if(input == ""){
                Toast.makeText(activity, "You must enter a number.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val millisInput = input.toLong() * 60000
            if(millisInput == 0L){
                Toast.makeText(activity, "Enter positive numbers only.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            setTime(millisInput)
            editTextInput.setText("")
        }

        btnStartPause.setOnClickListener {
            if(timerRunning){
                pauseTimer()
            }
            else{
                startTimer()
            }
        }
        btnReset.setOnClickListener {
            resetTimer()
        }

        btnClear.setOnClickListener {
            clearTime()
        }
    }

    private fun clearTime() {
        timeLeftInMillis = 0
        updateCountdownText()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerRunning = false
        updateWatchInterface()
//        Toast.makeText(activity,"Paused", Toast.LENGTH_SHORT).show()
    }

    private fun setTime(millisInput: Long) {
        startTimeInMillis = millisInput
        resetTimer()
    }

    private fun resetTimer() {
        timeLeftInMillis = startTimeInMillis
        updateCountdownText()
        updateWatchInterface()
    }

    private fun updateWatchInterface() {
        if(timerRunning){
            editTextInput.visibility = View.INVISIBLE
            btnSet.visibility = View.INVISIBLE
            btnReset.visibility = View.INVISIBLE
            btnClear.visibility = View.INVISIBLE
            btnStartPause.text = "Pause"
        }
        else{
            editTextInput.visibility = View.VISIBLE
            btnSet.visibility = View.VISIBLE
            btnClear.visibility = View.VISIBLE
            btnStartPause.text = "Start"
        }
        if(timeLeftInMillis < 1000){
            btnStartPause.visibility = View.INVISIBLE
        }
        else{
            btnStartPause.visibility = View.VISIBLE
        }
        if(timeLeftInMillis < startTimeInMillis){
            btnReset.visibility = View.VISIBLE
//            btnClear.visibility = View.VISIBLE
        }
        else {
            btnReset.visibility = View.INVISIBLE
            btnClear.visibility = View.INVISIBLE
        }
    }

    private fun updateCountdownText() {
        val hours: Int = (timeLeftInMillis.toInt() /1000) / 3600
        val minutes: Int = ((timeLeftInMillis.toInt() / 1000) % 3600) / 60
        val seconds: Int = (timeLeftInMillis.toInt() / 1000) % 60

        val timeLeft: String

        if(hours > 0){
            timeLeft = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        }
        else {
            timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
        textViewCountDown.text = timeLeft
    }

    private fun startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis
        countDownTimer = object: CountDownTimer(timeLeftInMillis, 1000){
            override fun onTick(p0: Long) {
                timeLeftInMillis = p0
                updateCountdownText()
            }

            override fun onFinish() {
                timerRunning = false
                updateWatchInterface()
            }
        }.start()
        timerRunning = true
        updateWatchInterface()
    }

    override fun onStop() {
        super.onStop()

        val activ = this.requireContext()
        val prefs: SharedPreferences = activ.getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putLong("startTimeInMillis", startTimeInMillis)
        editor.putLong("millisLeft", timeLeftInMillis)
        editor.putBoolean("timerRunning", timerRunning)
        editor.putLong("endTime", endTime)

        editor.apply()
        if(timerRunning) {
            countDownTimer.cancel()
        }
    }

    override fun onStart() {
        super.onStart()

        val activ = this.requireActivity()
        val prefs: SharedPreferences = activ.getSharedPreferences("prefs", MODE_PRIVATE)
//        val editor = prefs.edit()

        //update text and Interface
        updateCountdownText()
        updateWatchInterface()

        if(timerRunning){
            endTime = prefs.getLong("endTime", 0)
            timeLeftInMillis = endTime - System.currentTimeMillis()

            if(timeLeftInMillis < 0){
                timeLeftInMillis = 0
                timerRunning = false
                updateCountdownText()
                updateWatchInterface()
            }
            else {
                startTimer()
            }
        }
    }
}