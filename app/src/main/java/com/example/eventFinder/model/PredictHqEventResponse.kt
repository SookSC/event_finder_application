package com.example.eventFinder.model

import com.google.gson.annotations.SerializedName

data class PredictHqEventResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("overflow") val overflow: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("next") val next: String?,
    @SerializedName("results") val results: List<PredictHqEventItem>
)

data class PredictHqEventItem(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("title") val title: String,
    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String,
    @SerializedName("location") val location: ArrayList<Double>
)
