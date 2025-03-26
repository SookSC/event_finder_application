package com.example.eventFinder.model

data class EventResponse(
    var id: String,
    var description: String,
    var category: String,
    var title: String,
    var start: String,
    var end: String,
    var location: ArrayList<Double>
):java.io.Serializable
