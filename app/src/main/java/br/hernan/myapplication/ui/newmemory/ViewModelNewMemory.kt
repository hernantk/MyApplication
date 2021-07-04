package br.hernan.myapplication.ui.newmemory

import androidx.lifecycle.ViewModel
import br.hernan.myapplication.domain.dto.MemoryDto
import br.hernan.myapplication.domain.dto.RegisteMemoryDto
import br.hernan.myapplication.domain.repository.MemoryRepository

class ViewModelNewMemory(private val repository: MemoryRepository) :ViewModel(){



    fun save(memory:RegisteMemoryDto){
        repository.save(memory)
    }



}