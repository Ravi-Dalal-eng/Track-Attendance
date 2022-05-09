package com.trackravidalal.trackattendance.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.entities.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OurViewModel(private val ourRepository:OurRepository):ViewModel() {
    fun addSubjuct(subject: Subject){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.addSubject(subject)
        }
    }
    fun getSubjects():LiveData<List<Subject>>{
        return ourRepository.getSubjects()
    }
    fun deleteSubjuct(subject: Subject){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.deleteSubject(subject)
        }
    }
    fun addStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.addStudent(student)
        }
    }
    fun getStudents():LiveData<List<Student>>{
        return ourRepository.getStudents()
    }
    fun deleteStudentById(id: Long){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.deleteStudentById(id)
        }
    }
    fun getOneStudent(id:Long):LiveData<Student>{
        return ourRepository.getOneStudent(id)
    }
    fun updateStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.updateStudent(student)
        }
    }
    fun addAttendance(attendance: Attendance){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.addAttendance(attendance)
        }
    }
    fun getAttendance():LiveData<List<Attendance>>{
        return ourRepository.getAttendance()
    }
    fun deleteAttendanceById(id: Long){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.deleteAttendanceById(id)
        }
    }
    fun deleteAttendanceBySubject(subject: String){
        viewModelScope.launch(Dispatchers.IO){
            ourRepository.deleteAttendanceBySubject(subject)
        }
    }
    fun getAttendanceByDate(date:String):LiveData<List<Attendance>>{
        return ourRepository.getAttendanceByDate(date)
    }
    fun getAttendanceById(id: Long):LiveData<List<Attendance>>{
        return ourRepository.getAttendanceById(id)
    }
}