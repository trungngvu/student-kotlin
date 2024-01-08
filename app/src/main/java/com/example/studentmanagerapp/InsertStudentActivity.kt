package com.example.studentmanagerapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Calendar

class InsertStudentActivity : AppCompatActivity() {
    //set datePicker
    private lateinit var db: StudentDB
    private lateinit var studentDao: StudentDAO
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_student)
        // set auto complete text view
        val list = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        val autoCountry = findViewById<AutoCompleteTextView>(R.id.autoCountry)
        autoCountry.setAdapter(adapter)
        //
        findViewById<ImageButton>(R.id.btn_calendar).setOnClickListener() {
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    findViewById<EditText>(R.id.edt_date).setText("${dayOfMonth}/${month + 1}/${year}")
                },
                2023,
                12,
                31
            ).show()
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val selectedCountry = autoCountry.text.toString()
            val selectedDate = findViewById<EditText>(R.id.edt_date).text.toString()
            val insertMSSV = findViewById<EditText>(R.id.edt_mssv).text.toString()
            val insertName = findViewById<EditText>(R.id.edt_fullname).text.toString()
            student = Student(insertMSSV, insertName, selectedDate, selectedCountry)

            lifecycleScope.launch {

                val students = withContext(Dispatchers.IO) {
                    studentDao.insertStudent(student)
                }
            }

            val intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()

        }
        db = Room.databaseBuilder(
            applicationContext,
            StudentDB::class.java, "student_db"
        ).build()

        // Lấy thực thể DAO từ cơ sở dữ liệu
        studentDao = db.studentDao()
    }


}