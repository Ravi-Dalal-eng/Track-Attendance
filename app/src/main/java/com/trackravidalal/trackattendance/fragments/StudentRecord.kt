package com.trackravidalal.trackattendance.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.StudentListAdapter
import com.trackravidalal.trackattendance.databinding.FragmentStudentRecordBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory


class StudentRecord : Fragment() {
    private var _binding: FragmentStudentRecordBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel: OurViewModel
    val list= ArrayList<Student>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStudentRecordBinding.bind(view)
        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)

        val recyclerView=binding.studentList
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        shareViewModel.getStudents().observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            binding.studentList.adapter = StudentListAdapter(list,
                { position -> onListItemClicked(position) })
            (binding.studentList.adapter as StudentListAdapter).notifyDataSetChanged()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun onListItemClicked(position: Int) {

        val action=StudentRecordDirections.
        studentRecordFragmentToIndividualStudentRecordFragment(list[position].firstName,
            list[position].mobileNumber,list[position].id)
        findNavController().navigate(action)

    }
}