package ru.gb.themovie.view.fragments

import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.gb.themovie.R
import ru.gb.themovie.model.Const

class PersonBirthInMapFragment : Fragment() {

    companion object {
        fun getIntance(location: String): PersonBirthInMapFragment {
            val bundle = Bundle().apply {
                putString(Const.PERSON_BIRTH_LOCATION, location)
            }
            return PersonBirthInMapFragment().apply { arguments = bundle }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val location = getLocationByStringAsync()
        //LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(location))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    private fun getLocationByStringAsync(): LatLng {
        var location = LatLng(-34.0, 151.0)
        arguments?.let { arg ->
            val geocoder = Geocoder(requireContext())
            arg.getString(Const.PERSON_BIRTH_LOCATION)?.let {
                if (!it.equals("") && geocoder.getFromLocationName(it, 1).size > 0) {
                    location = LatLng(
                        geocoder.getFromLocationName(it, 1)[0].latitude,
                        geocoder.getFromLocationName(it, 1)[0].longitude
                    )
                }
            }
        }
        return location
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_birth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("MAP_FRAGMENT", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}