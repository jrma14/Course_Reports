package com.example.coursereports

import android.content.Intent
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CourseHelper {
    fun putExtras(intent: Intent, course: Course){
        intent.putExtra("course_title", course.course_title)
        intent.putExtra("term", course.term)
        intent.putExtra("professor", course.first_name + " " + course.last_name)
        intent.putExtra("average", course.overallAverage)
        intent.putExtra("grade", course.expectedGrade)
        intent.putExtra("hours", course.outsideClassTime)
        intent.putExtra("course_number", course.course_number)
        intent.putExtra("survey_size", course.surveySize.toString())
        intent.putExtra("year",course.year)
        intent.putExtra("id",course.id.toString())
    }
}