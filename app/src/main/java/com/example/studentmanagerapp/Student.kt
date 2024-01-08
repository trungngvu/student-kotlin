package com.example.studentmanagerapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student")
data class Student(
    @PrimaryKey var mssv: String,
    var fullName : String,
    var dateOfBirth: String,
    var country : String

): Serializable
