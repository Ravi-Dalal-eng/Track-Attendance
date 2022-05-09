package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.databinding.SubjectBinding
import com.trackravidalal.trackattendance.entities.Subject


class SubjectAdapter(private val subjectList:List<Subject>,
                     private val onItemDeleteClicked: (position: Int) -> Unit,
                     private val onItemSubjectClicked: (position: Int) -> Unit):
    RecyclerView.Adapter<SubjectAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapter.ViewHolder {
        val binding = SubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,onItemDeleteClicked,onItemSubjectClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subject = subjectList[position]
        holder.itemBinding.subjectText.text = subject.subject
    }

    override fun getItemCount() = subjectList.size

    class ViewHolder(val itemBinding: SubjectBinding,
                     private val onItemDeleteClicked: (position: Int) -> Unit,
                     private val onItemSubjectClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.subjectDelete.setOnClickListener(this)
            itemBinding.subjectText.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            if(v.id==itemBinding.subjectDelete.id)
            onItemDeleteClicked(position)
            else if(v.id==itemBinding.subjectText.id)
            onItemSubjectClicked(position)
        }
        }

}
