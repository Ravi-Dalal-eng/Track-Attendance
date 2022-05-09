package com.trackravidalal.trackattendance.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "attendance",
    indices = arrayOf(Index(value = ["date","subject","user_id"],unique = true)))
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val user_id:Long,
    val subject:String,
    val date:String,
    val present:Int
)