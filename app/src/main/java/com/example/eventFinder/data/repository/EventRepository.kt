package com.example.eventFinder.data.repository

import com.example.eventFinder.data.model.PredictHqEventResponse
import com.example.eventFinder.data.model.TicketmasterEventResponse
import com.example.eventFinder.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository {
    fun getPredictHqEvents(radiusKm: Int, lat: Double, long: Double, authToken: String,
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

    fun getTicketmasterEvents(radiusKm: Int, lat: Double, long: Double, authToken: String,
                              callback: (TicketmasterEventResponse?) -> Unit) {
        // TODO: Radius unit "km" currently hardcoded. Give option to use "miles"
        RetrofitInstance.ticketMasterEventService.getTicketmasterEvents(authToken, composeLatLongQuery(lat, long), radiusKm, "km")
            .enqueue(object : Callback<TicketmasterEventResponse> {
            override fun onResponse(call: Call<TicketmasterEventResponse>, response: Response<TicketmasterEventResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(it)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<TicketmasterEventResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    private fun composeWithinQuery(radiusKm: Int, lat: Double, long: Double): String {
        return radiusKm.toString() + "km@" + lat.toString() + "," + long.toString()
    }

    private fun composeLatLongQuery(lat: Double, long: Double): String {
        return "$lat,$long"
    }
}
