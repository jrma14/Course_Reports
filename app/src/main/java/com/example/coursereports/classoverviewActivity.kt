package com.example.coursereports

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class classoverviewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.classoverview)
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        val view: View = supportActionBar!!.customView
        val title: TextView = view.findViewById(R.id.page_title)
        title.text = intent.getStringExtra("course_number")

        val course_title = intent.getStringExtra("course_title")
        val course_title_text: TextView = findViewById(R.id.course_title)
        course_title_text.text = course_title


        val term = intent.getStringExtra("term")
        val term_text: TextView = findViewById(R.id.course_term)
        term_text.setText(term + " Term")

        val professor = intent.getStringExtra("professor")
        val professor_text: TextView = findViewById(R.id.professor)
        professor_text.setText(professor)

        val surveySize = intent.getStringExtra("survey_size")
        val surveySizeText: TextView = findViewById(R.id.surveySize)
        surveySizeText.setText("Survey Size: " + surveySize)


        val avg = intent.getStringExtra("average")
        val avg_text: TextView = findViewById(R.id.avg)
        avg_text.setText("Overall Rating:\n" + avg)

        val grade = intent.getStringExtra("grade")
        val grade_text: TextView = findViewById(R.id.grade)

        grade_text.setText("Expected Grade:\n" + grade)

        val hours = intent.getStringExtra("hours")
        val hours_text: TextView = findViewById(R.id.hours)
        hours_text.setText(hours + "hrs/week")
    }

}