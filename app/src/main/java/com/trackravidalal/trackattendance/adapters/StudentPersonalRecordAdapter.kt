package com.trackravidalal.trackattendance.adapters


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trackravidalal.trackattendance.databinding.StudentRecordItemBinding
import com.trackravidalal.trackattendance.models.StudentPersonalRecord

class StudentPersonalRecordAdapter(private val list:List<StudentPersonalRecord>,
                                  private val criteria:Int):
                            RecyclerView.Adapter<StudentPersonalRecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentPersonalRecordAdapter.ViewHolder {
        val binding = StudentRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentPersonalRecordAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentPersonalRecordAdapter.ViewHolder, position: Int) {
        val subject_record = list[position]
        val percentage=(subject_record.present*100)/subject_record.total
      holder.itemBinding.apply {
          individualSubject.setText(subject_record.subject)
          individualPercentage.setText(percentage.toString().plus("%"))
          progressBar.setProgressWithAnimation(percentage.toFloat(),1500)
          totalClasses.setText(holder.itemBinding.totalClasses.text.toString().plus(": ${subject_record.total}"))
          presentClasses.setText(holder.itemBinding.presentClasses.text.toString().plus(": ${subject_record.present}"))
          absentClasses.setText(holder.itemBinding.absentClasses.text.toString().plus(": ${subject_record.total-subject_record.present}"))
      }
        if (percentage<criteria){
         holder.itemBinding.apply {
             individualPercentage.setTextColor(Color.parseColor("#FF1100"))
            progressBar.progressBarColor=Color.RED
         }
        }
        else{
           holder.itemBinding.apply {
               individualPercentage.setTextColor(Color.parseColor("#00FF0A"))
               progressBar.progressBarColor=Color.GREEN
           }
        }
    }

    override fun getItemCount() = list.size


    class ViewHolder(val itemBinding: StudentRecordItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)



}