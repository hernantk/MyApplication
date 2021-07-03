package br.hernan.myapplication.domain.repository

import br.hernan.myapplication.domain.dto.MemoryDto
import br.hernan.myapplication.domain.dto.RegisteMemoryDto
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate

class MemoryRepository(private val firestore: FirebaseFirestore) {

    fun findAll(onSuccess: (List<MemoryDto>) -> Unit){

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

    fun save(memory:RegisteMemoryDto,onSuccess:()-> Unit){

        firestore.collection(COLLETION)
            .add(memory)
            .addOnSuccessListener { onSuccess() }
    }


    companion object{
        private const val COLLETION = "logs"
    }
    }



