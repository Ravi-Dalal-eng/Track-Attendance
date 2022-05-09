package com.trackravidalal.trackattendance.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trackravidalal.trackattendance.entities.Attendance

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendance(attendance: Attendance)

    @Query("SELECT * FROM attendance")
    fun getAttendances(): LiveData<List<Attendance>>

    @Query("DELETE FROM attendance WHERE subject = :subject")
    suspend fun deleteBySubject(subject:String)

    @Query("DELETE FROM attendance WHERE user_id = :user_id")
    suspend fun deleteByUserId(user_id:Long)

    @Query("SELECT * FROM attendance WHERE date= :date")
    fun getAttendanceByDate(date:String): LiveData<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE user_id= :user_id")
    fun getAttendanceById(user_id: Long): LiveData<List<Attendance>>
}