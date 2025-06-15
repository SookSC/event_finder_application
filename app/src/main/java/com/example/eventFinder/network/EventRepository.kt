package com.example.eventFinder.network

import com.example.eventFinder.model.PredictHqEventResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {
    fun getPredictHqEvents(radiusKm: Double, lat: Double, long: Double, authToken: String,
                           callback: (PredictHqEventResponse?) -> Unit) {
        RetrofitInstance.predictHqEventService.getPredictHqEvents(authToken, composeWithinQuery(radiusKm, lat, long))
            .enqueue(object : Callback<PredictHqEventResponse> {
                override fun onResponse(call: Call<PredictHqEventResponse>, response: Response<PredictHqEventResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback(it)
                        }
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<PredictHqEventResponse>, t: Throwable) {
                    callback(null)
                }
            })
        }

    private fun composeWithinQuery(radiusKm: Double, lat: Double, long: Double): String {
        return radiusKm.toString() + "km@" + lat.toString() + "," + long.toString()
    }
}
