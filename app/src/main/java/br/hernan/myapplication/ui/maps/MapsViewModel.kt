package br.hernan.myapplication.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.hernan.myapplication.domain.repository.MemoryRepository
import com.google.android.gms.maps.model.LatLng

class MapsViewModel(private val repository: MemoryRepository) : ViewModel() {


    private val mLocationResult: MutableLiveData<List<LatLng>> = MutableLiveData()

    val locationResult: LiveData<List<LatLng>>
        get() = mLocationResult


    fun listLocation(){
        repository.findAllLocation(
            mLocationResult::postValue,
        )
    }





}