package com.example.pomodoroclock

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.EditText
import android.widget.Toast

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import com.example.pomodoroclock.databinding.FragmentPomFeedBinding
import com.example.pomodoroclock.databinding.FragmentPomodoroBinding

class PomodoroFragment : Fragment() {

    private var _binding: FragmentPomodoroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPomodoroBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check if the user clicked start
        binding.tvStart.setOnClickListener{
            if(binding.tvStudy.text!!.isNotEmpty() && binding.tvBreak.text!!.isNotEmpty() && binding.tvRounds.text!!.isNotEmpty()) {
                //grab all the user input
                val studyTime = binding.tvStudy.text.toString()
                val breakTime = binding.tvBreak.text.toString()
                val roundCount = binding.tvRounds.text.toString()
                //create a bundle to pass to feed frag
                val bundle = Bundle()
                bundle.putString("study",studyTime)
                bundle.putString("break",breakTime)
                bundle.putString("round",roundCount)
                val pomFeedFragment = PomFeedFragment()
                pomFeedFragment.arguments = bundle
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fContainer, pomFeedFragment)
                        .commit() }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
