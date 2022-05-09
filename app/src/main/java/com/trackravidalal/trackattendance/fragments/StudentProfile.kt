package com.trackravidalal.trackattendance.fragments

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.StudentAdapter
import com.trackravidalal.trackattendance.databinding.FragmentStudentProfileBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory


class StudentProfile : Fragment() {
    private var _binding:FragmentStudentProfileBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel: OurViewModel
    val list= ArrayList<Student>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStudentProfileBinding.bind(view)
        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)

        val recyclerView=binding.profileRecyclerView
        recyclerView.layoutManager= LinearLayoutManager(requireContext())

        shareViewModel.getStudents().observe(viewLifecycleOwner) {
            list.clear()
            list.addAll(it)
            binding.profileRecyclerView.adapter = StudentAdapter(list,
                { position,v -> onListItemClicked(position,v) })
            (binding.profileRecyclerView.adapter as StudentAdapter).notifyDataSetChanged()
        }
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun onListItemClicked(position: Int,v: View) {
        val popupMenu=PopupMenu(requireContext(),v)
        popupMenu.menuInflater.inflate(R.menu.update_delete,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object :PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item!!.itemId){
                    R.id.update_student-> {
                        val action=StudentProfileDirections.
                        profileFragmentToUpdateFragment(list[position].id)
                        findNavController().navigate(action)
                    }
                        R.id.delete_student->{
                            val action=StudentProfileDirections
                                .profileFragmentToConfirmDeleteStudent(id = list[position].id,
                                    name = list[position].firstName+" "+list[position].lastName)
                            findNavController().navigate(action)

                        }
                }
                return true
            }
        })
        popupMenu.show()
    }

}