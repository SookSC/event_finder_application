package com.example.eventFinder.network

import com.example.eventFinder.model.PredictHqEventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EventService {
    @GET("events/")
    fun getPredictHqEvents(
        @Header("Authorization") authToken: String,
        @Query("within") within: String
    ): Call<PredictHqEventResponse>
}
