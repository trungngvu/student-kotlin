package com.example.studentmanagerapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDAO {
    @Query("SELECT * FROM student")
    fun getAllStudent(): List<Student>
    @Insert
    suspend fun insertStudent(student:Student)

    @Delete
    suspend fun deleteStudent(student: Student)
    @Update
    fun updateStudent(student: Student)
}