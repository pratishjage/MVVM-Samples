package com.example.mvvmsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmsample.R
import com.example.mvvmsample.Utils.AppConstants.ADDRESS
import com.example.mvvmsample.Utils.AppConstants.LAT
import com.example.mvvmsample.Utils.AppConstants.LNG
import com.example.mvvmsample.Utils.AppConstants.NAME
import com.example.mvvmsample.db.UserAddress

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_maps.*
import java.lang.StringBuilder

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val extras = intent.extras

        val name = extras.getString(NAME)
        val lat = extras.getString(LAT)
        val lng = extras.getString(LNG)
        val address = Gson().fromJson(extras.getString(ADDRESS), UserAddress::class.java)

        val addressString = StringBuilder()
        addressString.append("Address :").append(address.street).append(",").append(address.suite)
            .append(",").append(address.city).append("-").append(address.zipcode)
        address_txt.text = addressString.toString()

        // Add a marker in Sydney and move the camera
        val userLocation = LatLng(lat.toDouble(), lng.toDouble())
        mMap.addMarker(MarkerOptions().position(userLocation).title(name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14.0f))
    }
}
