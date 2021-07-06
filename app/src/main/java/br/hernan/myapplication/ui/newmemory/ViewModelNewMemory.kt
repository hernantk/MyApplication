package br.hernan.myapplication.ui.newmemory

import androidx.lifecycle.ViewModel
import br.hernan.myapplication.domain.dto.RegisterMemoryDto
import br.hernan.myapplication.domain.repository.MemoryRepository

class ViewModelNewMemory(private val repository: MemoryRepository) :ViewModel(){

    fun save(memory:RegisterMemoryDto){
        repository.save(memory)
    }



}