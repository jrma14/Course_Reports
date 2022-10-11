package com.example.coursereports

import retrofit2.Response
import retrofit2.http.GET

interface PlanetscaleAPI {
    @GET("/")
    suspend fun getAllCourses(): Response<List<Course>>
}