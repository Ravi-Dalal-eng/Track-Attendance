package com.trackravidalal.trackattendance.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.DateAdapter
import com.trackravidalal.trackattendance.databinding.FragmentPastAttendanceBinding
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory


class PastAttendance : Fragment() {

private var _binding:FragmentPastAttendanceBinding?=null
    private val binding get()=_binding!!
    private lateinit var shareViewModel:OurViewModel
    val datelistnormal=ArrayList<String>()
    val datelistsearch=ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_attendance, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentPastAttendanceBinding.bind(view)

        val ourDatabase= OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository= OurRepository(ourDatabase)
        shareViewModel= ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)
        binding.searchEditText.setText("")
        val recyclerView=binding.pastAttandance
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        shareViewModel.getAttendance().observe(viewLifecycleOwner){
            datelistnormal.clear()
            val hashSet=HashSet<String>()
            for (a in 0..it.size-1){
                if(!hashSet.contains(it[a].date)){
                   hashSet.add(it[a].date)
                    datelistnormal.add(it[a].date)
                }
            }
            datelistsearch.clear()
            datelistsearch.addAll(datelistnormal)
            binding.pastAttandance.adapter= DateAdapter(datelistsearch,
            { position -> onListItemClick(position) })
            (binding.pastAttandance.adapter as DateAdapter).notifyDataSetChanged()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int,
                before: Int, count: Int) {
                if(s.length>0)
                    binding.cancelEditText.visibility=View.VISIBLE
                else
                    binding.cancelEditText.visibility=View.INVISIBLE
                datelistsearch.clear()
                for (i in 0..datelistnormal.size-1){
                    if(datelistnormal[i].contains(s))
                        datelistsearch.add(datelistnormal[i])
                }
                binding.pastAttandance.adapter= DateAdapter(datelistsearch,
                    { position -> onListItemClick(position) })
                (binding.pastAttandance.adapter as DateAdapter).notifyDataSetChanged()
            }
        })
       binding.cancelEditText.setOnClickListener {
           binding.searchEditText.setText("")
           binding.cancelEditText.visibility=View.INVISIBLE
           val currentFocusview = requireActivity().currentFocus

           val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           imm.hideSoftInputFromWindow(currentFocusview?.windowToken, 0)
       }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun onListItemClick(position: Int) {

        val action=PastAttendanceDirections.
        pastAttendanceFragmentToPastSubjectFragment(datelistsearch[position])
        findNavController().navigate(action)
        binding.searchEditText.setText("")
    }
}