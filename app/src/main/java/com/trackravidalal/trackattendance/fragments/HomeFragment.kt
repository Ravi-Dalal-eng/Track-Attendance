package com.trackravidalal.trackattendance.fragments

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.SubjectAdapter
import com.trackravidalal.trackattendance.databinding.FragmentHomeBinding
import com.trackravidalal.trackattendance.entities.Subject
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel: OurViewModel
    val list= ArrayList<Subject>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentHomeBinding.bind(view)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            val pattern = "EEEE, dd MMM yyyy"
            val simpleDateFormat =  SimpleDateFormat(pattern)
            val date: String = simpleDateFormat.format(Date())
         binding.showDate.visibility=View.VISIBLE
            binding.showDate.text=date
        }

        val ourDatabase=OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository=OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(),OurViewModelFactory(ourRepository))
          .get(OurViewModel::class.java)


           binding.addSubject.setOnClickListener {
            val alertDialog = AlertDialog.Builder(view.context)
            val factory = LayoutInflater.from(it.context)
            val our_view = factory.inflate(R.layout.add_subject_dialog_box,null)
            alertDialog.setView(our_view)
            alertDialog.setCancelable(false)
            alertDialog.setNeutralButton(R.string.cancel){_,_->}
            alertDialog.setPositiveButton(R.string.add){
                    _,_->
            val text=our_view.findViewById<EditText>(R.id.dialog_edit_text).text.toString()

                if(TextUtils.isEmpty(text)){
                    Snackbar.make(
                        requireActivity().findViewById(R.id.drawer_layout),
                        "Please enter the subject name to add...",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else{
                    val subject=Subject(0,text)

                    GlobalScope.launch(Dispatchers.IO) {
                        shareViewModel.addSubjuct(subject) }
               }
            }
               alertDialog.show()
        }
        val recyclerView=binding.subjects
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

       shareViewModel.getSubjects().observe(viewLifecycleOwner) {
           list.clear()
           list.addAll(it)
           binding.subjects.adapter = SubjectAdapter(list,
               { position -> onListItemDeleteClick(position) },
               { position -> onListItemSubjectClick(position) })
           (binding.subjects.adapter as SubjectAdapter).notifyDataSetChanged()
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun onListItemDeleteClick(position: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.delete)
        dialog.setMessage(R.string.delete_message)
       dialog.setIcon(R.drawable.delete_dialog_box_icon)
        dialog.setNeutralButton(R.string.cancel){_,_->}
        dialog.setPositiveButton(R.string.delete){
                _,_->
            shareViewModel.deleteAttendanceBySubject(list[position].subject)
            shareViewModel.deleteSubjuct(list[position])
        }
        dialog.show()

    }
    private fun onListItemSubjectClick(position: Int) {
        val action=HomeFragmentDirections.
        homeFragmentToAttendanceFragment(list[position].subject,binding.showDate.text.toString())
        findNavController().navigate(action)
    }

}