package br.hernan.myapplication.ui.selectMap


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ActivityMapsSelection : AppCompatActivity(),OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy{
        FragmentMapsBinding.inflate(layoutInflater)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mLocationClient : FusedLocationProviderClient
    private lateinit var point: LatLng



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupMap()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_confirm, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnConfirm -> {
                savePointGoNewMemory(LatLng(point.latitude, point.longitude))
            }
        }
        return true }


    private fun setupMap(){
        binding.map.onCreate(null)
        binding.map.onResume()
        binding.map.getMapAsync(this)
        setupLocationClient()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { location ->
            refreshPoint(location.latitude,location.longitude)
            addMarker(point)
        } }


    private fun savePointGoNewMemory(point:LatLng){
        setResult(RESULT_OK,Intent().putExtra("POINT",point))
        finish()
    }

    private fun setupLocationClient(){
        mLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            requestLocationUpdates()
        } }
    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates(){
        val locationRequest = LocationRequest.create().apply {
            interval = 15000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        mLocationClient.requestLocationUpdates(
            locationRequest,
            mLocationCallback,
            Looper.getMainLooper())
    }
    private val mLocationCallback = object: LocationCallback(){


        override fun onLocationResult(result: LocationResult) {
            mMap.clear()
            refreshPoint(result.lastLocation.latitude,result.lastLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(point.latitude, point.longitude),12f,0f,0f)))
            addMarker(point)
        }
    }

    private fun refreshPoint(latitude: Double,longitude: Double){
        point= LatLng(latitude,longitude)
    }
    private fun addMarker(point: LatLng){
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(point))
    }
}