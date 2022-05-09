package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.R
import com.trackravidalal.trackattendance.databinding.StudentListItemBinding
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.utils.Converters

class StudentListAdapter(private val studentList:List<Student>,
                         private val onItemClick: (position: Int) -> Unit):
    RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {
    val colour= arrayOf(
        R.drawable.orange_background, R.drawable.blue_background
        , R.drawable.green_background, R.drawable.purple_background, R.drawable.red_background)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListAdapter.ViewHolder {
        val binding = StudentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentListAdapter.ViewHolder(binding, onItemClick)
    }
    override fun onBindViewHolder(holder: StudentListAdapter.ViewHolder, position: Int) {
        val student = studentList[position]
        val converters= Converters()
        holder.itemBinding.apply {
            studentRollNo.text=student.rollNumber
            studentListName.text=student.firstName.plus(" ${student.lastName}")
            rootLayout.setBackgroundResource(colour[position%5])
        }
        if(student.image!=null)
            holder.itemBinding.studentListPic.setImageBitmap(converters.toBitmap(student.image))
    }



    override fun getItemCount() = studentList.size

    class ViewHolder(val itemBinding: StudentListItemBinding, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.rootLayout.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClick(position)
        }
    }

    }