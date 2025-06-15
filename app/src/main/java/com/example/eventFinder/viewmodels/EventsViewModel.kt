package com.example.eventFinder.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventFinder.network.EventRepository
import com.example.eventFinder.model.PredictHqEventResponse
import com.example.eventFinder.model.TicketmasterEventResponse

class EventsViewModel : ViewModel() {
    private val repository = EventRepository()

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _location

    fun setLocation(location: Location) {
        _location.value = location
    }

    private val _predictHqEventsData = MutableLiveData<PredictHqEventResponse>()
    val predictHqEventsData: LiveData<PredictHqEventResponse> get() = _predictHqEventsData

    private val _ticketMasterEventsData = MutableLiveData<TicketmasterEventResponse>()
    val ticketMasterEventsData: LiveData<TicketmasterEventResponse> get() = _ticketMasterEventsData

    private val _radiusInput = MutableLiveData<Int>()
    val radiusInput: LiveData<Int> get() = _radiusInput

    fun setRadius(radius: Int) {
        _radiusInput.value = radius
    }

    fun fetchEvents(predictHqAuthToken: String, ticketmasterAuthToken: String) {
        if (radiusInput.value != null && location.value != null) {
            repository.getPredictHqEvents(radiusInput.value!!, location.value!!.latitude, location.value!!.longitude, predictHqAuthToken) {
                _predictHqEventsData.value = it
            }
            repository.getTicketmasterEvents(radiusInput.value!!, location.value!!.latitude, location.value!!.longitude, ticketmasterAuthToken) {
                _ticketMasterEventsData.value = it
            }
        }
    }
}
