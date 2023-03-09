package com.example.pomodoroclock

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pomodoroclock.databinding.FragmentPomFeedBinding

class PomFeedFragment : Fragment() {

    private var countDownTimer: CountDownTimer? = null
    private lateinit var studyArg: String
    private lateinit var breakArg: String
    private lateinit var roundArg: String

    private var studyMinute: Int?= null
    private var breakMinute: Int?= null
    private var roundCount: Int?= null

    private var nRound = 1
    private var isStudy = true
    private var isStop = false
    private var mp: MediaPlayer? = null

    private var _binding: FragmentPomFeedBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPomFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        studyArg = arguments?.getString("study")!!
        breakArg = arguments?.getString("break")!!
        roundArg = arguments?.getString("round")!!

        studyMinute = studyArg.toInt()* 60 * 1000
        breakMinute = breakArg.toInt()* 60 * 1000
        roundCount = roundArg.toInt()
        binding.btn.text= "Stop"
        binding.tvRound.text = "$nRound/$roundCount"
        setRestTimer()
        binding.btn.setOnClickListener {
            resetOrStart()
        }
        binding.tvBack.setOnClickListener{
            clearAttribute()
            val pomodoroFragment = PomodoroFragment()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fContainer, pomodoroFragment)
                    .commit() }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRestTimer(){
        //playSound()
        binding.btn.visibility = View.INVISIBLE
        binding.tvStatus.text = "Get Ready"
        binding.progressBar.progress = 0
        binding.progressBar.max = 10
        countDownTimer= object:CountDownTimer(10500,1000){
            override fun onTick(p0: Long)
            {
                binding.progressBar.progress= (p0/1000).toInt()
                binding.tvCounter.text = (p0/1000).toString()
            }
            override fun onFinish()
            {
                mp?.reset()
                if (isStudy) { setupStudyView() }
                else { setupBreakView() }
            }
        }.start()
    }
    private fun setupBreakView(){
        binding.tvStatus.text = "Break Time"
        binding.progressBar.max = breakMinute!!/1000

        if(countDownTimer != null)
            countDownTimer = null
        setBreakTimer()
    }
    private fun setBreakTimer(){
        countDownTimer = object : CountDownTimer(breakMinute!!.toLong()+500,1000){
            override fun onTick(p0: Long) {
                binding.progressBar.progress=(p0/1000).toInt()
                binding.tvCounter.text = createTimeLabel(p0.toInt())
            }
            override fun onFinish() {
                isStudy = true
                setRestTimer()
            }
        }.start()
    }
    private fun setupStudyView(){
        binding.btn.visibility = View.VISIBLE
        binding.tvRound.text = "$nRound/$roundCount"
        binding.tvStatus.text = "study time"
        binding.progressBar.max = studyMinute!!/1000

        if(countDownTimer != null)
            countDownTimer = null

        setStudyTimer()
    }
   /* private fun playSound() {
        try{
            val soundUrl = Uri.parse("android.resource://com.example.pomodoroclock/"+R.raw.alarm)
            mp = MediaPlayer.create(this,soundUrl)
            mp?.isLooping = false
            mp?.start()
        }catch(e:Exception){
            e.printStackTrace()
        }
    }*/
    private fun setStudyTimer( ){
        countDownTimer = object: CountDownTimer(studyMinute!!.toLong()+500,1000){
            override fun onTick(p0: Long) {
                binding.progressBar.progress= (p0 / 1000).toInt()
                binding.tvCounter.text = createTimeLabel(p0.toInt())
            }
            override fun onFinish() {
                if (nRound < roundCount!!)
                {
                    isStudy = false
                    setRestTimer()
                    nRound++
                }else{
                    clearAttribute()
                    binding.tvStatus.text = "You have finished your rounds :3"
                }
            }
        }.start()
    }
    private fun clearAttribute(){
        binding.tvStatus.text = "Press play button to restart"
        binding.btn.text = "Play"
        binding.progressBar.progress = 0
        binding.tvCounter.text ="0"
        nRound = 1
        binding.tvRound.text = "$nRound/$roundCount"
        countDownTimer?.cancel()
        mp?.reset()
        isStop = true
    }
    private fun createTimeLabel(time: Int):String{
        var timeLabel = ""
        val minutes = time / 1000 / 60
        val seconds = time / 1000 % 60

        if(minutes < 10) timeLabel += "0"
        timeLabel += "$minutes:"

        if(seconds < 10) timeLabel += "0"
        timeLabel += seconds

        return timeLabel
    }
    private fun resetOrStart(){
        if(isStop){
            binding.btn.text = "Stop"
            setRestTimer()
            isStop= false
        }else{
            clearAttribute()
        }
    }
}