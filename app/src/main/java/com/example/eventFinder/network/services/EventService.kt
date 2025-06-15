package com.example.eventFinder.network.services

import com.example.eventFinder.data.model.PredictHqEventResponse
import com.example.eventFinder.data.model.TicketmasterEventResponse
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

    @GET("events.json")
    fun getTicketmasterEvents(
        @Query("apikey") authToken: String,
        @Query("latlong") latLong: String,
        @Query("radius") radius: Int,
        @Query("unit") radiusUnit: String
    ): Call<TicketmasterEventResponse>
}
