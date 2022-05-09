package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.ProfileBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.Converters


class StudentAdapter (private val studentList:List<Student>, private val onItemClick: (position: Int,v:View) -> Unit):
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    val colour= arrayOf(R.drawable.orange_background,R.drawable.blue_background
        ,R.drawable.green_background,R.drawable.purple_background,R.drawable.red_background)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        val binding = ProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = studentList[position]
        val converters=Converters()
       holder.itemBinding.apply {
           profileRollNo.text=student.rollNumber
           profileName.text=student.firstName.plus(" ${student.lastName}")
           profileDateOfBirth.text=student.dateOfBirth
           profileEmailId.text=student.email
           profilePhoneNo.text=student.mobileNumber
           profileBackground.setBackgroundResource(colour[position%5])
       }
        if(student.image!=null)
            holder.itemBinding.profilePic.setImageBitmap(converters.toBitmap(student.image))
    }


    override fun getItemCount() = studentList.size

    class ViewHolder(val itemBinding: ProfileBinding, private val onItemClick: (position: Int,v:View) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.profileMoreVert.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClick(position,itemBinding.profileMoreVert)
        }
    }

}