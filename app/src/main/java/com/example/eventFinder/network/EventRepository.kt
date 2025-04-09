package com.example.eventFinder.network

import com.example.eventFinder.model.EventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {
    fun getEvents(radiusKm: Double, lat: Double, long: Double, authToken: String,
                   callback: (EventResponse?) -> Unit) {
        val eventService = RetrofitInstance.eventService

        eventService.getEvents(authToken, composeWithinQuery(radiusKm, lat, long))
            .enqueue(object : Callback<EventResponse> {
                override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                    callback(null)
                }
            })
        }

    private fun composeWithinQuery(radiusKm: Double, lat: Double, long: Double): String {
        return radiusKm.toString() + "km@" + lat.toString() + "," + long.toString()
    }
}
