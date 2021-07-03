package br.hernan.myapplication.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment : Fragment(),OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy{
        FragmentMapsBinding.inflate(layoutInflater)
    }
    private val viewModel: MapsViewModel by viewModel()

    private lateinit var mMap: GoogleMap
    private lateinit var mLocationClient : FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupMap()

    }



    private fun setupMap(){
        val mapFragment =childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapClickListener { location ->
            addMarker(location.latitude,location.longitude)
        }
        // Add a marker in Sydney and move the camera
        addMarker(-26.078572,-53.0516805)
    }
    private fun addMarker(latitude:Double,longitude:Double){
        mMap.clear()
        val point = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(point))
    }


}