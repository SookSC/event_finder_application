package com.example.eventFinder

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.viewmodels.EventsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class EventsFragment : Fragment() {
    private val viewModel: EventsViewModel by viewModels()
    private val authToken = "Bearer " + BuildConfig.PREDICTHQ_API_KEY

    private lateinit var progressBar: ProgressBar
    private lateinit var locationOffView: LinearLayout
    private lateinit var errorLayout: LinearLayout
    private lateinit var locationDataLayout: LinearLayout
    private lateinit var enableLocationButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var latTextView: TextView
    private lateinit var longTextView: TextView

    private lateinit var eventAdapter: EventAdapter

    private lateinit var client: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_bar)
        locationOffView = view.findViewById(R.id.location_off_layout)
        errorLayout = view.findViewById(R.id.error_loading_data_layout)
        locationDataLayout = view.findViewById(R.id.location_data_display_layout)
        enableLocationButton = view.findViewById(R.id.enable_location_access_button)
        recyclerView = view.findViewById(R.id.recycler_view)
        latTextView = view.findViewById(R.id.lat_text_view)
        longTextView = view.findViewById(R.id.long_text_view)

        eventAdapter = EventAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = eventAdapter

        client = LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.location.observe(viewLifecycleOwner) { location ->
            location?.let {
                latTextView.text = location.latitude.toString()
                longTextView.text = location.longitude.toString()

                val radius = 5.0 // TODO: Retrieve radius from user input instead of hardcoded value
                viewModel.setRadius(radius)
                viewModel.fetchEvents(authToken)

                showLayout(locationAccessed = true)
            }
        }

        viewModel.eventsData.observe(viewLifecycleOwner) { eventsResponse ->
            eventsResponse?.let { eventAdapter.updateEvents(eventsResponse.results) }
        }

        val fineLocationAccess = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationAccess = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocationAccess == PackageManager.PERMISSION_GRANTED || coarseLocationAccess == PackageManager.PERMISSION_GRANTED) {
            // TODO: If only coarse location access granted, include a button in top bar to turn on fine location access
            // TODO: Include button for user to choose to refresh their location
            // TODO: Include option for user to search by city instead of using their current location

            // Location access already granted. Show data
            loadLocationBasedData()
        } else {
            showLayout(locationAccessed = false)

            enableLocationButton.setOnClickListener {
                // Show educational UI if necessary
                val showCoarseLocationEduUI = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
                if (showCoarseLocationEduUI || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showEducationalUIForLocationUse(showCoarseLocationEduUI)
                }

                // Request permissions
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            }
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> { loadLocationBasedData() }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> { loadApproximateLocationBasedData() }
            else -> {}
        }
    }

    // UI explaining use of user's location before requesting location permission access
    private fun showEducationalUIForLocationUse(forBothFineAndCoarseLocation: Boolean) {
        // TODO
    }

    @SuppressLint("MissingPermission")
    private fun loadLocationBasedData() {
        progressBar.visibility = View.VISIBLE
        locationOffView.visibility = View.GONE

        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.lastLocation.addOnSuccessListener { task ->
                if (task != null) { viewModel.setLocation(task) }
                else { requestHighAccuracyLocation() }
            }.addOnFailureListener { e: Exception ->
                Toast.makeText(requireContext(), "Unable to get location. Please try again.", Toast.LENGTH_SHORT).show()
                showLayout(locationAccessed = false)
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun requestHighAccuracyLocation() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMinUpdateIntervalMillis(5000)
            .build()

        client.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        viewModel.setLocation(location)
                        client.removeLocationUpdates(this)
                    } else {
                        Toast.makeText(requireContext(), "Unable to get location. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    private fun loadApproximateLocationBasedData() {
        // TODO
    }

    private fun showLayout(locationAccessed: Boolean) {
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.GONE
        locationOffView.isVisible = !locationAccessed
        locationDataLayout.isVisible = locationAccessed
    }
}
