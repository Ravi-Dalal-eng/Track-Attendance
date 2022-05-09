package com.trackravidalal.trackattendance.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trackravidalal.trackattendance.entities.Student

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Query("SELECT * FROM student")
    fun getStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM student WHERE id=:id ")
    fun getOneStudent(id: Long): LiveData<Student>

    @Query("DELETE FROM student WHERE id =:studentId")
    suspend fun deleteStudentById(studentId:Long)
}















