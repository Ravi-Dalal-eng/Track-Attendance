package com.trackravidalal.trackattendance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.databinding.PastDateItemBinding

class DateAdapter(private val list:List<String>,
                  private val onItemClicked: (position: Int) -> Unit):
    RecyclerView.Adapter<DateAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.ViewHolder {
        val binding = PastDateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateAdapter.ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: DateAdapter.ViewHolder, position: Int) {
        val value = list[position]
        holder.itemBinding.pastDate.text=value
    }

    override fun getItemCount() = list.size


    class ViewHolder(val itemBinding: PastDateItemBinding,
                     private val onItemClicked: (position: Int) -> Unit):
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        init {
            itemBinding.pastDate.setOnClickListener(this)

        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)

        }
    }
}