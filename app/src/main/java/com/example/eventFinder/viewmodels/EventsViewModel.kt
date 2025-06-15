package com.example.eventFinder.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventFinder.network.EventRepository
import com.example.eventFinder.model.PredictHqEventResponse

class EventsViewModel : ViewModel() {
    private val repository = EventRepository()

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _location

    fun setLocation(location: Location) {
        _location.value = location
    }

    private val _predictHqEventsData = MutableLiveData<PredictHqEventResponse>()
    val predictHqEventsData: LiveData<PredictHqEventResponse> get() = _predictHqEventsData

    private val _radiusInput = MutableLiveData<Double>()
    val radiusInput: LiveData<Double> get() = _radiusInput

    fun setRadius(radius: Double) {
        _radiusInput.value = radius
    }

    fun fetchEvents(predictHqAuthToken: String) {
        if (radiusInput.value != null && location.value != null) {
            repository.getPredictHqEvents(radiusInput.value!!, location.value!!.latitude, location.value!!.longitude, predictHqAuthToken) {
                _predictHqEventsData.value = it
            }
        }
    }
}
