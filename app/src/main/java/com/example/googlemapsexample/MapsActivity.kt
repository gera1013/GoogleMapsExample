package com.example.googlemapsexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val zoom = 15F
        val UVG = LatLng(14.604063, -90.489259)
        mMap.addMarker(MarkerOptions().position(UVG).title("Marcador en la UVG"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UVG, zoom))
        setMapLongClick(mMap)
        setPoiClick(mMap)
        var success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        if (!success) Log.e("LOL", "Style parsing failed")
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean{
        val inflater = menuInflater
        inflater.inflate(R.menu.maps_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.normal_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.hybrid_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                return true
            }
            R.id.satellite_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.terrain_map -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener {
            val snippet = String.format(Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", it.latitude, it.longitude)
            map.addMarker(MarkerOptions().position(it).title(getString(R.string.dropped_pin)).snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))}
    }

    private fun setPoiClick(map: GoogleMap){
        map.setOnPoiClickListener{
            val poiMarker = mMap.addMarker(MarkerOptions().position(it.latLng).title(it.name))
            poiMarker.showInfoWindow()
        }
    }
}
