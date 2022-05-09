package com.trackravidalal.trackattendance.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.AttendanceAdapter
import com.trackravidalal.trackattendance.databinding.FragmentAttendanceBinding
import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AttendanceFragment : Fragment() {
    private var _binding: FragmentAttendanceBinding?=null
    private val binding get()=_binding!!

    private lateinit var shareViewModel: OurViewModel
    val list= ArrayList<Student>()
    lateinit var attendance:Array<Int>

    private val args:AttendanceFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentAttendanceBinding.bind(view)
        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)
        val subject=args.subject
        val date=args.date

        val recyclerView=binding.attendanceRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

            shareViewModel.getStudents().observe(viewLifecycleOwner) {
           list.clear()
           list.addAll(it)
                attendance= Array(it.size,  { i -> i * 0 })
           binding.attendanceRecyclerView.adapter = AttendanceAdapter(list,
               { position,value -> onListRadioPresentClick(position,value) },
               { position,value -> onListRadioAbsentClick(position,value) })
           (binding.attendanceRecyclerView.adapter as AttendanceAdapter).notifyDataSetChanged()
       }
        binding.submit.setOnClickListener {

            for (i in 0..attendance.size-1){
               val attendance=Attendance(0,list[i].id,subject,date, attendance[i])
                  GlobalScope.launch(Dispatchers.IO)
                  {
                      shareViewModel.addAttendance(attendance)
                   }
            }

            Snackbar.make(
                requireActivity().findViewById(R.id.drawer_layout),
                "Attendance submitted successfully...",
                Snackbar.LENGTH_LONG
            ).show()
            findNavController().popBackStack()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun onListRadioPresentClick(position: Int,value:Int) {
        attendance[position]=value
        for (i in 0..attendance.size-1) {
           if (attendance[i]==0)
               return
        }
        binding.apply {
            submit.isEnabled=true
            submit.setStrokeColorResource(R.color.background)
            submit.setTextColor(resources.getColor(R.color.background))
        }
    }
    private fun onListRadioAbsentClick(position: Int,value:Int) {
        attendance[position]=value
        for (i in 0..attendance.size-1) {
            if (attendance[i]==0)
                return
        }
      binding.apply {
          submit.isEnabled=true
          submit.setStrokeColorResource(R.color.background)
          submit.setTextColor(resources.getColor(R.color.background))
      }
    }
}