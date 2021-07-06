package br.hernan.myapplication.ui.memory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.hernan.myapplication.domain.dto.MemoryDto
import br.hernan.myapplication.domain.repository.MemoryRepository

class MemoryViewModel(private val repository: MemoryRepository) : ViewModel() {

    private val mMemoryResult: MutableLiveData<List<MemoryDto>> = MutableLiveData()
    val memoryResult: LiveData<List<MemoryDto>>
        get() = mMemoryResult


    fun listMemory(){
        repository.findAllMemory(
            mMemoryResult::postValue,
        )

    }
}