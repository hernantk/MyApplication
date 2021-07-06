package br.hernan.myapplication.domain.repository

import br.hernan.myapplication.domain.dto.MemoryDto
import br.hernan.myapplication.domain.dto.RegisterMemoryDto
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class MemoryRepository(private val firestore: FirebaseFirestore) {

    fun findAllMemory(onSuccess: (List<MemoryDto>) -> Unit){

        firestore.collection(COLLETION)
            .get()
            .addOnSuccessListener { memorys ->
                val result = memorys.map{ memory ->
                    MemoryDto(memory.id,
                        LocalDate.parse(memory.getString("date")),
                        memory.getString("city")?:"",
                        memory.getString("description")?:"",
                        memory.getString("image")?:"")

                }
                onSuccess(result)
            }

    }

    fun findAllLocation(onSuccess: (List<LatLng>) -> Unit){

        firestore.collection(COLLETION)
            .get()
            .addOnSuccessListener { location ->
                val result = location.map{ local ->
                    LatLng(
                        local.getDouble("latitude")!!,
                        local.getDouble("longitude")!!
                    )
                }
                onSuccess(result)
            }

    }

    fun save(memory:RegisterMemoryDto){
        firestore.collection(COLLETION)
            .add(memory)
    }


    companion object{
        private const val COLLETION = "memory"
    }
    }



