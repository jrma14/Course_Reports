package com.example.coursereports

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanetscaleAPI {

    @GET("/allCourses")
    suspend fun getAllCourses(): Response<List<Course>>

    @GET("/course")
    fun getCourse(@Query("id") id: Int): Call<Course>
}