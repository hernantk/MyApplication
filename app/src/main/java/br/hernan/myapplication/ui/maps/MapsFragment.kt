package br.hernan.myapplication.ui.maps

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.fragment.app.Fragment
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMapsBinding
import br.hernan.myapplication.ui.newmemory.ActivityNewMemory
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapsFragment( ) : Fragment(), OnMapReadyCallback {

    private val binding: FragmentMapsBinding by lazy{
        FragmentMapsBinding.inflate(layoutInflater)
    }

    private lateinit var mMap: GoogleMap

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnAdd -> {
               save(LatLng(latitude,longitude))
                }
                 }
        return true }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
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

        addMarker(-26.078572,-53.0516805)

    }
    private fun addMarker(latitude:Double,longitude:Double){
        mMap.clear()
        val point = LatLng(latitude,longitude)
        mMap.addMarker(MarkerOptions().position(point))
    }


    private fun save(point:LatLng){
        val bundle = Bundle()
        bundle.putDouble("LATITUDE",point.latitude)
        bundle.putDouble("LONGITUDE",point.longitude)
        val intent = Intent(requireContext(),ActivityNewMemory::class.java).putExtras(bundle)
        startActivity(intent)
    }


}