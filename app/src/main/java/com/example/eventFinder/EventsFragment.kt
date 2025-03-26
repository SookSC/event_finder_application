package com.example.eventFinder

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventFinder.model.EventResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class EventsFragment : Fragment() {

    // Events Fragment layout components
    private lateinit var progressBar: ProgressBar
    private lateinit var locationOffView: TextView
    private lateinit var errorLayout: LinearLayout
    private lateinit var locationDataLayout: LinearLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var latTextView: TextView
    private lateinit var longTextView: TextView

    private lateinit var client: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout to view object
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        progressBar = view.findViewById(R.id.progress_bar)
        locationOffView = view.findViewById(R.id.location_off_text_view)
        errorLayout = view.findViewById(R.id.error_loading_data_layout)
        locationDataLayout = view.findViewById(R.id.location_data_display_layout)

        // Show events in recyclerView
        val dataSet = arrayListOf(
            EventResponse("", "", "Sports","Hockey Game",
                "March 26 @ 5PM", "March 26 @ 9PM", arrayListOf(1.234, 5.678)),
            EventResponse("", "", "Music","R&B Concert",
                "March 26 @ 5PM", "March 26 @ 9PM", arrayListOf(1.234, 5.678)),
            EventResponse("", "", "Arts & Crafts","Pottery Painting Workshop",
                "March 26 @ 5PM", "March 26 @ 9PM", arrayListOf(1.234, 5.678)),
            EventResponse("", "", "Community","Garbage Cleanup Day",
                "March 23 @ 12PM", "March 23 @ 6PM", arrayListOf(1.234, 5.678)),
        )

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = EventAdapter(requireContext(), dataSet)

        latTextView = view.findViewById(R.id.lat_text_view)
        longTextView = view.findViewById(R.id.long_text_view)

        client = LocationServices.getFusedLocationProviderClient(requireContext())

        // Display inflated view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // If location permissions are not granted, request permissions from user
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_DENIED) {

            // Show educational UI if necessary
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                showEducationalUIForLocationUse(false)
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showEducationalUIForLocationUse(true)
            }
            // Request permissions
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        } else {
            loadLocationBasedData()
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> { loadLocationBasedData() }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> { loadApproximateLocationBasedData() }
            else -> { showNoLocationAccessMessage() }
        }
    }

    // UI explaining use of user's location before requesting location permission access
    private fun showEducationalUIForLocationUse(forOnlyFineLocation: Boolean) {
        // TODO
    }

    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun loadLocationBasedData() {
        progressBar.visibility = View.GONE
        locationOffView.visibility = View.GONE
        errorLayout.visibility = View.GONE
        locationDataLayout.visibility = View.VISIBLE

        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    latTextView.setText(location.latitude.toString())
                    longTextView.setText(location.longitude.toString())
                }
            }
        }
    }

    private fun loadApproximateLocationBasedData() {
        // TODO
    }

    private fun showNoLocationAccessMessage() {
        progressBar.visibility = View.GONE
        locationOffView.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        locationDataLayout.visibility = View.GONE
    }

}
