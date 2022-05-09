package com.trackravidalal.trackattendance.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.adapters.StudentPersonalRecordAdapter
import com.trackravidalal.trackattendance.databinding.FragmentIndividualStudentRecordBinding
import com.trackravidalal.trackattendance.models.StudentPersonalRecord
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory
import com.google.android.material.snackbar.Snackbar


class IndividualStudentRecord : Fragment() {
    private var _binding: FragmentIndividualStudentRecordBinding? = null
    private val binding get() = _binding!!

    private lateinit var shareViewModel: OurViewModel
    val list = ArrayList<StudentPersonalRecord>()
    private val args: IndividualStudentRecordArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_student_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIndividualStudentRecordBinding.bind(view)
        val ourDatabase = OurDatabase.getDatabase(requireActivity().applicationContext)
        val ourRepository = OurRepository(ourDatabase)
        shareViewModel = ViewModelProvider(requireActivity(), OurViewModelFactory(ourRepository))
            .get(OurViewModel::class.java)

        val phoneNo = args.phoneNo
        val id = args.id
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val value = sharedPref.getInt(getString(R.string.criteria_value), 75)
        val recycler_view = binding.recyclerView
        recycler_view.layoutManager = LinearLayoutManager(requireContext())

        shareViewModel.getAttendanceById(id).observe(viewLifecycleOwner) {
            val hashMap = HashMap<String, StudentPersonalRecord>()
            for (i in 0..it.size - 1) {
                if (hashMap.containsKey(it[i].subject)) {
                    val our_value = hashMap.getValue(it[i].subject)
                    our_value.total = our_value.total + 1
                    if (it[i].present == 1)
                        our_value.present = our_value.present + 1
                } else {
                    val studentRecord: StudentPersonalRecord
                    if (it[i].present == 1)
                        studentRecord = StudentPersonalRecord(it[i].subject, 1, 1)
                    else
                        studentRecord = StudentPersonalRecord(it[i].subject, 0, 1)
                    hashMap.put(it[i].subject, studentRecord)
                }
            }
            val values=hashMap.values
            for(i in values)
                list.add(i)
           recycler_view.adapter=StudentPersonalRecordAdapter(list,value)
            (recycler_view.adapter as StudentPersonalRecordAdapter).notifyDataSetChanged()
        }



        binding.sendSms.setOnClickListener {
            val alertDialog = AlertDialog.Builder(view.context)
            val factory = LayoutInflater.from(it.context)
            val our_view = factory.inflate(R.layout.sms_dialog_box,null)
            alertDialog.setView(our_view)
            alertDialog.setCancelable(false)
            alertDialog.setNeutralButton(R.string.cancel){_,_->}
            alertDialog.setPositiveButton(R.string.send){
                    _,_->
                val text=our_view.findViewById<EditText>(R.id.sms_edit_text).text.toString()

                if(TextUtils.isEmpty(text)){
                    Snackbar.make(
                        requireActivity().findViewById(R.id.drawer_layout),
                        "Please type a message to send...",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                else{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phoneNo"))
                    intent.putExtra("sms_body", text)
                    startActivity(intent)
                }
            }

            alertDialog.show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


    }