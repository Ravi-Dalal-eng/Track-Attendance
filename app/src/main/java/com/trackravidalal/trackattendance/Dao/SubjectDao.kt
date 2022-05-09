package com.trackravidalal.trackattendance.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.trackravidalal.trackattendance.entities.Subject

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubject(subject: Subject)

    @Delete
    suspend fun deleteSubject(subject: Subject)

    @Update
    suspend fun updateSubject(subject: Subject)

    @Query("SELECT * FROM subject")
    fun getSubjects(): LiveData<List<Subject>>
}