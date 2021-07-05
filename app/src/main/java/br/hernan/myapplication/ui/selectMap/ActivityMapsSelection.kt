package br.hernan.myapplication.ui.selectMap


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import br.hernan.myapplication.ui.newmemory.ActivityNewMemory
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
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

    private var latitude: Double = -53.05404603481293
    private var longitude: Double = -26.074973855427512



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
                setLocation(LatLng(latitude, longitude))
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
            longitude=location.longitude
            latitude=location.latitude
            addMarker(latitude,longitude)
        }
        mMap.setOnMapLoadedCallback { googleMap.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(latitude, longitude),12f,0f,0f)
            )) }
    }

    private fun setLocation(point:LatLng){
        val location = Intent()
        location.putExtra("LATITUDE",point.latitude)
        location.putExtra("LONGITUDE",point.longitude)
        setResult(RESULT_OK,location)
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
            longitude=result.lastLocation.longitude
            latitude=result.lastLocation.latitude
            addMarker(latitude,longitude)
            mLocationClient.removeLocationUpdates(this)
        }
    }


    private fun addMarker(latitude:Double,longitude:Double){
        mMap.clear()
        val point = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(point))
    }
}