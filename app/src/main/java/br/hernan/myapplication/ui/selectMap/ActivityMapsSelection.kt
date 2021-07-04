package br.hernan.myapplication.ui.selectMap


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import br.hernan.myapplication.ui.newmemory.ActivityNewMemory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ActivityMapsSelection : AppCompatActivity(),OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy{
        FragmentMapsBinding.inflate(layoutInflater)
    }

    private lateinit var mMap: GoogleMap

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { location ->
            addMarker(location.latitude,location.longitude)
            longitude=location.longitude
            latitude=location.latitude
        }
    }
    private fun addMarker(latitude:Double,longitude:Double){
        mMap.clear()
        val point = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(point))

    }
    private fun setLocation(point:LatLng){
        val location = Intent()
        location.putExtra("LATITUDE",point.latitude)
        location.putExtra("LONGITUDE",point.longitude)
        setResult(RESULT_OK,location)
        finish()
    }








}