package com.example.coursereports

class OnClickListener(val clickListener: (c: Class) -> Unit) {
    fun onClick(course: Class) = clickListener(course)
}