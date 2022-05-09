package com.trackravidalal.trackattendance.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "student",indices = arrayOf(Index(value = ["rollNumber"], unique = true)))
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val firstName:String,
    val lastName:String,
    val mobileNumber:String,
    val rollNumber:String,
    val email:String,
    val dateOfBirth:String,
    val image:ByteArray? = null
)








