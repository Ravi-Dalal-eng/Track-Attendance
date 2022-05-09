package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.databinding.AttendanceBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.Converters

class AttendanceAdapter(private val studentList:List<Student>,
                        private val onItemPresentClicked: (position: Int,value:Int) -> Unit,
                        private val onItemAbsentClicked: (position: Int,value:Int) -> Unit):
                        RecyclerView.Adapter<AttendanceAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceAdapter.ViewHolder {
        val binding = AttendanceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttendanceAdapter.ViewHolder(binding,onItemPresentClicked,onItemAbsentClicked)
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.ViewHolder, position: Int) {
        val student = studentList[position]
        val converters= Converters()
        holder.itemBinding.apply {
            rollNo.text=student.rollNumber
            attendanceName.text=student.firstName.plus(" ${student.lastName}")
        }

        if(student.image!=null)
            holder.itemBinding.attendancePic.setImageBitmap(converters.toBitmap(student.image))
    }


    override fun getItemCount() = studentList.size

    class ViewHolder(val itemBinding: AttendanceBinding,
                     private val onItemPresentClicked: (position: Int,value:Int) -> Unit,
                     private val onItemAbsentClicked: (position: Int,value:Int) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.present.setOnClickListener(this)
            itemBinding.absent.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition

            if(v.id==itemBinding.present.id)
                onItemPresentClicked(position,1)
            else if(v.id==itemBinding.absent.id)
                onItemAbsentClicked(position,-1)
        }
    }
}