package com.example.pomodoroclock.alarmAdapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pomodoroclock.AlarmListViewModel
import com.example.pomodoroclock.ClockFragment
import com.example.pomodoroclock.R
import com.example.pomodoroclock.room.TableInfo
import kotlinx.android.synthetic.main.fragment_alarm_list.view.*


class ListOfAlarmsFragment : Fragment() {
    private lateinit var alarmsListViewModel: AlarmListViewModel
    private lateinit var addAlarm: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            alarmsListViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application))[AlarmListViewModel::class.java]
    }
    //creates/initializes the view but we must return a view or null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm_list, container, false)

        val adapter = AlarmRecyclerView()
        val recyclerView = view.fragment_listalarms_recylerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        alarmsListViewModel.AlarmListViewModel(requireActivity().application)
        alarmsListViewModel.getAlarmsLiveData().observe(viewLifecycleOwner, Observer { TableInfo ->
            adapter.setAlarms(TableInfo)

        })

    return view
    }

    //    3rd Event that loads up makes sure view is fully loaded
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addAlarm = view.findViewById(R.id.fragment_listalarms_addAlarm)
        addAlarm.setOnClickListener {
            val fragment: Fragment = ClockFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fContainer, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }


    fun onToggle(alarm: TableInfo) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(requireContext())
            alarmsListViewModel.update(alarm)
        } else {
            alarm.schedule(context)
            alarmsListViewModel.update(alarm)
        }
    }

}

