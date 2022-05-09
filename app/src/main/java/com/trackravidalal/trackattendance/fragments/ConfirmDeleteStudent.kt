package com.trackravidalal.trackattendance.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.FragmentConfirmDeleteStudentBinding
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ConfirmDeleteStudent : BottomSheetDialogFragment() {
    private var _binding: FragmentConfirmDeleteStudentBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel: OurViewModel
    private val args:ConfirmDeleteStudentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_delete_student, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentConfirmDeleteStudentBinding.bind(view)
        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)

        val id=args.id
        val name=args.name
        binding.deleteMessage.setText(binding.deleteMessage.text.toString().plus(" $name"))
        binding.cancel.setOnClickListener {
            dismiss()
        }
        binding.delete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                shareViewModel.deleteAttendanceById(id)
            shareViewModel.deleteStudentById(id)
            }
            dismiss()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}