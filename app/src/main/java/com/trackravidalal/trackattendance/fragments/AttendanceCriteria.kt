package com.trackravidalal.trackattendance.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

import androidx.fragment.app.Fragment

import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.FragmentAttendanceCriteriaBinding
import com.google.android.material.snackbar.Snackbar


class AttendanceCriteria : Fragment() {
    private var _binding: FragmentAttendanceCriteriaBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance_criteria, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentAttendanceCriteriaBinding.bind(view)
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val value = sharedPref.getInt(getString(R.string.criteria_value), 75)
        binding.percent.setText("$value".plus("%"))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            binding.seekBar.setProgress(value,true)

        else
            binding.seekBar.setProgress(value)


        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.percent.setText("$progress".plus("%"))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                with (sharedPref.edit()) {
                    putInt(getString(R.string.criteria_value), seekBar.progress)
                    apply()
                }
                Snackbar.make(
                    requireActivity().findViewById(R.id.drawer_layout),
                    "Attendance criteria set successfully... ",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}