package com.trackravidalal.trackattendance.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.StudentAttendanceListBinding

import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.utils.Converters
import com.trackravidalal.trackattendance.utils.OurRepository
import com.trackravidalal.trackattendance.utils.OurViewModel
import com.trackravidalal.trackattendance.utils.OurViewModelFactory


class StudentPastAttendanceAdapter(private val owner: LifecycleOwner,
                                   private val context:FragmentActivity,
                                   private val list:List<Attendance>):
             RecyclerView.Adapter<StudentPastAttendanceAdapter.ViewHolder>()
{

             val ourDatabase= OurDatabase.getDatabase(context.applicationContext)
            val ourRepository= OurRepository(ourDatabase)
             val shareViewModel= ViewModelProvider(context, OurViewModelFactory(ourRepository))
                                     .get(OurViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentPastAttendanceAdapter.ViewHolder {
        val binding = StudentAttendanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentPastAttendanceAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentPastAttendanceAdapter.ViewHolder, position: Int) {
        val attendance = list[position]
        val converter=Converters()
        shareViewModel.getOneStudent(attendance.user_id).observe(owner){
            holder.itemBinding.studentRollNo.setText(it.rollNumber)
            holder.itemBinding.studentName.setText(it.firstName.plus(" ${it.lastName}"))
            if(it.image!=null)
                holder.itemBinding.studentPic.setImageBitmap(converter.toBitmap(it.image))
        }
        if (attendance.present==1){
        holder.itemBinding.apply {
            presentOrNot.setText(R.string.present)
            presentOrNot.setTextColor(Color.parseColor("#00FF0A"))
        }
        }
        else{
           holder.itemBinding.apply {
               presentOrNot.setText(R.string.absent)
               presentOrNot.setTextColor(Color.parseColor("#FF1100"))
           }
        }
    }

    override fun getItemCount() = list.size


    class ViewHolder(val itemBinding: StudentAttendanceListBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}