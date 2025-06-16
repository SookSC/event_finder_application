package com.sookeongcho.eventHub.data.model

import com.google.gson.annotations.SerializedName

// Hierarchy 1
data class TicketmasterEventResponse(
    @SerializedName("_embedded") val embedded: EmbeddedEvents,
)

// Hierarchy 2
data class EmbeddedEvents(
    @SerializedName("events") val ticketMasterEvents: List<TicketmasterEventItem>,
)

// Hierarchy 3
data class TicketmasterEventItem(
    val name: String,
    val id: String,
    val url: String,
    val images: List<Image>?,
    val dates: Dates,
    val classifications: List<Classification>?,
    val description: String?,
    @SerializedName("_embedded") val embedded: Embedded?,
    val place: PlaceOrVenue?,
)

// Hierarchy 4
data class Image(
    val url: String,
    val width: Int,
    val height: Int,
    val attribution: String,
)

data class Dates(
    val start: DateDetails,
    val end: DateDetails?,
    val timezone: String?,
)

data class Classification(
    val primary: Boolean,
    val segment: ClassificationItem, // primary genre for an entity (Music, Sports, Arts, etc.)
    val genre: ClassificationItem, // secondary genre (Rock, Classical, Animation, etc)
    val subgenre: ClassificationItem, // tertiary genre (Alternative Rock, Ambient Pop, etc)
)

data class Embedded(
    val venues: List<PlaceOrVenue>,
)

data class PlaceOrVenue(
    val address: Address?,
    val city: City?,
    val state: State?,
    val country: Country?,
    val postalCode: String?,
    val location: Location?,
    val name: String,
    val description: String?,
    val type: String?,
)

// Hierarchy 5
data class DateDetails(
    val localDate: String?,
    val localTime: String?,
    val dateTime: String?,
    val noSpecificTime: Boolean?,
)

data class ClassificationItem(
    val id: String,
    val name: String,
)

data class Address(
    val line1: String,
    val line2: String?,
    val line3: String?,
)

data class City(
    val name: String,
)

data class State(
    val stateCode: String,
    val name: String,
)

data class Country(
    val countryCode: String,
    val name: String,
)

data class Location(
    val longitude: String,
    val latitude: String,
)