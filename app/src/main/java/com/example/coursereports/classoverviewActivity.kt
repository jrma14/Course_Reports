package com.example.coursereports

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class classoverviewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.classoverview)
        actionBar?.title = intent.getStringExtra("course_number")

        val course_title = intent.getStringExtra("course_title")
        val course_title_text: TextView = findViewById(R.id.course_title)
        course_title_text.text = course_title


        val term = intent.getStringExtra("term")
        val term_text: TextView = findViewById(R.id.course_term)
        term_text.setText(term)

        val professor = intent.getStringExtra("professor")
        val professor_text: TextView = findViewById(R.id.professor)
        professor_text.setText(professor)


        val avg = intent.getStringExtra("average")
        val avg_text: TextView = findViewById(R.id.avg)
        avg_text.setText(avg)

        val grade = intent.getStringExtra("grade")
        val grade_text: TextView = findViewById(R.id.grade)
        grade_text.setText(grade)

        val hours = intent.getStringExtra("hours")
        val hours_text: TextView = findViewById(R.id.hours)
        hours_text.setText(hours)
    }

}