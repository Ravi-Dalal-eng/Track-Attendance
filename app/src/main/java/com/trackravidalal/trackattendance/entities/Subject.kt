package com.trackravidalal.trackattendance.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "subject",indices = arrayOf(Index(value = ["subject"], unique = true)))
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val subject:String
)
