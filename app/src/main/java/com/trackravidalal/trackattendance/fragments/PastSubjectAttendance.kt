package com.trackravidalal.trackattendance.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.StudentPastAttendanceAdapter
import com.trackravidalal.trackattendance.adapters.SubjectListAdapter
import com.trackravidalal.trackattendance.databinding.FragmentPastSubjectAttendanceBinding
import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.entities.Subject
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import java.util.ArrayList


class PastSubjectAttendance : Fragment() {
    private var _binding: FragmentPastSubjectAttendanceBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel: OurViewModel
    val list1= ArrayList<Subject>()
    val list2=ArrayList<Attendance>()
    private val args:PastSubjectAttendanceArgs by navArgs()
     lateinit var date:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_subject_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentPastSubjectAttendanceBinding.bind(view)

        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)

        val recyclerView1=binding.subjectRecyclerView
        recyclerView1.layoutManager= LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        val recyclerView2=binding.studentRecyclerView
        recyclerView2.layoutManager= LinearLayoutManager(requireContext())

         date=args.date

       shareViewModel.getSubjects().observe(viewLifecycleOwner) {
           list1.clear()
           list1.addAll(it)
           binding.subjectRecyclerView.adapter = SubjectListAdapter(list1,
               { position -> onItemClick(position) })
           (binding.subjectRecyclerView.adapter as SubjectListAdapter).notifyDataSetChanged()
           if (list1.size>0){
               binding.subjectName.setText(list1[0].subject)
               shareViewModel.getAttendanceByDate(date).observe(viewLifecycleOwner){
                   list2.clear()

                   for (i in 0..it.size-1){
                       if(it[i].subject.equals(list1[0].subject))
                           list2.add(it[i])
                   }

                   binding.studentRecyclerView.adapter=StudentPastAttendanceAdapter(viewLifecycleOwner,requireActivity(),list2)
                   (binding.studentRecyclerView.adapter as StudentPastAttendanceAdapter).notifyDataSetChanged()
                  }
           }
       }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun onItemClick(position: Int) {
    val subject=list1[position]
        if(binding.subjectName.text.toString().equals(subject.subject))
            return
        binding.subjectName.setText(subject.subject)
        shareViewModel.getAttendanceByDate(date).observe(viewLifecycleOwner){
            list2.clear()
            for (i in 0..it.size-1){
                if(it[i].subject.equals(subject.subject))
                    list2.add(it[i])
            }

            binding.studentRecyclerView.adapter=StudentPastAttendanceAdapter(viewLifecycleOwner,requireActivity(),list2)
            (binding.studentRecyclerView.adapter as StudentPastAttendanceAdapter).notifyDataSetChanged()
        }
    }
}