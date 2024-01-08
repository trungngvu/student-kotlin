package com.example.studentmanagerapp

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDB: RoomDatabase() {
    abstract fun studentDao(): StudentDAO
    companion object {
        @Volatile
        private var INSTANCE : StudentDB? = null
        fun getInstance(context: Context): StudentDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StudentDB::class.java,
                    "student_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}