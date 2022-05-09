package com.trackravidalal.trackattendance.utils
import androidx.lifecycle.LiveData
import com.trackravidalal.trackattendance.Database.OurDatabase
import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.entities.Subject


class OurRepository(private val ourDatabase: OurDatabase) {
   suspend fun addSubject(subject: Subject){
       ourDatabase.subjectDao().addSubject(subject)
   }
    fun getSubjects():LiveData<List<Subject>>{
        return ourDatabase.subjectDao().getSubjects()
    }
    suspend fun deleteSubject(subject: Subject){
        ourDatabase.subjectDao().deleteSubject(subject)
    }

   suspend fun addStudent(student: Student){
        ourDatabase.studentDao().addStudent(student)
    }
    fun getStudents():LiveData<List<Student>>{
        return ourDatabase.studentDao().getStudents()
    }
    suspend fun deleteStudentById(id: Long){
        ourDatabase.studentDao().deleteStudentById(id)
    }
    fun getOneStudent(id:Long):LiveData<Student>{
        return ourDatabase.studentDao().getOneStudent(id)
    }
    suspend fun updateStudent(student: Student){
        ourDatabase.studentDao().updateStudent(student)
    }

    suspend fun addAttendance(attendance: Attendance){
        ourDatabase.attendanceDao().addAttendance(attendance)
    }

    fun getAttendance():LiveData<List<Attendance>>{
        return ourDatabase.attendanceDao().getAttendances()
    }
    suspend fun deleteAttendanceById(id: Long){
        ourDatabase.attendanceDao().deleteByUserId(id)
    }
    suspend fun deleteAttendanceBySubject(subject:String){
        ourDatabase.attendanceDao().deleteBySubject(subject)
    }
    fun getAttendanceByDate(date:String):LiveData<List<Attendance>>{
        return ourDatabase.attendanceDao().getAttendanceByDate(date)
    }
    fun getAttendanceById(id: Long):LiveData<List<Attendance>>{
        return ourDatabase.attendanceDao().getAttendanceById(id)
    }
}