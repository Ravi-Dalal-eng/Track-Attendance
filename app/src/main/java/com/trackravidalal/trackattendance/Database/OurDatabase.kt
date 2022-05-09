package com.trackravidalal.trackattendance.Database

import android.content.Context
import androidx.room.*
import com.trackravidalal.trackattendance.Dao.AttendanceDao
import com.trackravidalal.trackattendance.Dao.StudentDao
import com.trackravidalal.trackattendance.Dao.SubjectDao
import com.trackravidalal.trackattendance.entities.Attendance
import com.trackravidalal.trackattendance.entities.Student
import com.trackravidalal.trackattendance.entities.Subject
import com.trackravidalal.trackattendance.utils.Converters

@Database(entities = [Student::class, Subject::class,Attendance::class], version = 1)
@TypeConverters(Converters::class)
abstract class OurDatabase: RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun subjectDao(): SubjectDao
    abstract fun attendanceDao():AttendanceDao
    companion object{
        @Volatile
        private var INSTANCE:OurDatabase?=null

        fun getDatabase(context: Context):OurDatabase{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context,
                        OurDatabase::class.java,
                        "myDatabase").build()
                }
            }
            return INSTANCE!!
        }
    }
}