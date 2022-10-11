package com.example.coursereports

class OnClickListener(val clickListener: (c: Course) -> Unit) {
    fun onClick(course: Course) = clickListener(course)
}