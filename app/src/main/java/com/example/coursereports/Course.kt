package com.example.coursereports

data class Course(
    val course_title: String,
    val course_number: String,
    val term: String,
    val year: String,
    val id: Int,
    val first_name: String,
    val last_name: String,
    val overallAverage: String,
    val expectedGrade: String,
    val outsideClassTime: String,
    val surveySize: Int,
    var favorited: Boolean = false
) {
//    override fun toString(): String {
//        return "{name: " + name + ",number:" + number + ",term:" + term + ",professor:" + professor + ",average:" + average + ",grade:" + grade + ",hours:" + hours + "}"
//     }
}