package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.databinding.PastSubjectListBinding
import com.trackravidalal.trackattendance.entities.Subject

class SubjectListAdapter(private val list1:List<Subject>,
                         private val onItemClick: (position: Int) -> Unit):
                         RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectListAdapter.ViewHolder {
        val binding = PastSubjectListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectListAdapter.ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SubjectListAdapter.ViewHolder, position: Int) {
        val subject = list1[position]
        holder.itemBinding.subject.text = subject.subject
    }

    override fun getItemCount() = list1.size



    class ViewHolder(val itemBinding: PastSubjectListBinding,
                     private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.subject.setOnClickListener(this)

        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClick(position)

        }
    }

}