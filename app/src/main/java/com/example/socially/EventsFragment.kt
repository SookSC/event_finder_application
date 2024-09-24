package com.example.socially

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.recyclerview.widget.RecyclerView

class EventsFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var locationOffView: TextView
    private lateinit var errorLayout: LinearLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout to view object
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        progressBar = view.findViewById(R.id.progress_bar)
        locationOffView = view.findViewById(R.id.location_off_text_view)
        errorLayout = view.findViewById(R.id.error_loading_data_layout)
        recyclerView = view.findViewById(R.id.recycler_view)

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

    private fun loadLocationBasedData() {
        // TODO
    }

    private fun loadApproximateLocationBasedData() {
        // TODO
    }

    private fun showNoLocationAccessMessage() {
        progressBar.visibility = View.GONE
        locationOffView.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment EventsFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            EventsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}