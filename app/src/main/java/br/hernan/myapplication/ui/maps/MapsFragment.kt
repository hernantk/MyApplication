package br.hernan.myapplication.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import br.hernan.myapplication.ui.newmemory.ActivityNewMemory
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment( ) : Fragment(), OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy{
        FragmentMapsBinding.inflate(layoutInflater)
    }

    private val viewModel: MapsViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    private lateinit var mLocationClient : FusedLocationProviderClient

    private var latitude: Double = -53.05404603481293
    private var longitude: Double = -26.074973855427512

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.listLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnAdd -> {
               save(LatLng(latitude,longitude)) } }
        return true }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupLocationClient()

    }

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
        viewModel.locationResult.observe(viewLifecycleOwner){local -> local.forEach{l->addMarker(l)} }
        mMap.setOnMapLoadedCallback { googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(latitude, longitude),12f,0f,0f))) }

    }
    private fun addMarker(latitude:Double,longitude:Double){
        val point = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(point))
    }

    private fun addMarker(point: LatLng){
        mMap.addMarker(MarkerOptions().position(point))
    }


    private fun save(point:LatLng){
        val bundle = Bundle()
        bundle.putDouble("LATITUDE",point.latitude)
        bundle.putDouble("LONGITUDE",point.longitude)
        val intent = Intent(requireContext(),ActivityNewMemory::class.java).putExtras(bundle)
        startActivity(intent)
    }

    private val mLocationCallback = object: LocationCallback(){

        override fun onLocationResult(result: LocationResult) {
            longitude=result.lastLocation.longitude
            latitude=result.lastLocation.latitude
            addMarker(result.lastLocation.longitude,result.lastLocation.latitude)
        }

    }

    private val permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted->
        if(granted==true){
            requestLocationUpdates()
        }
    }

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

    private fun setupLocationClient(){
        mLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            requestLocationUpdates()
        }else{
            permissionsLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }}


}