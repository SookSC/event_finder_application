package com.example.eventFinder.network

import com.example.eventFinder.model.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EventService {
    @GET("events/")
    fun getEvents(
        @Header("Authorization") authToken: String,
        @Query("within") within: String
    ): Call<EventResponse>
}
