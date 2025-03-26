package com.example.eventFinder.network

import android.content.Context
import android.widget.Toast
import com.example.eventFinder.BuildConfig
import com.example.eventFinder.model.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {
    suspend fun getEvents(context: Context, radiusKm: Double, lat: Double, long: Double, callback: (EventResponse) -> Unit) {
        val authToken = BuildConfig.PREDICTHQ_API_KEY

        val eventService = RetrofitInstance.eventService

        val call: Call<EventResponse> =  eventService.getEvents(authToken,composeWithinQuery(radiusKm, lat, long))

        call.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if(response.isSuccessful){
                    val events: EventResponse = response.body() as EventResponse
                    callback(events)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                Toast.makeText(context, "Request Fail: Unable to retrieve events", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun composeWithinQuery(radiusKm: Double, lat: Double, long: Double): String {
        return radiusKm.toString() + "km@" + lat.toString() + "," + long.toString()
    }
}