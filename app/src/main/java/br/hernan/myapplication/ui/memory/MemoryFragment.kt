package br.hernan.myapplication.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.hernan.myapplication.databinding.FragmentMemoryBinding
import br.hernan.myapplication.domain.dto.MemoryDto
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryFragment : Fragment() {

    private val binding: FragmentMemoryBinding by lazy{
        FragmentMemoryBinding.inflate(layoutInflater)
    }
    private val viewModel: MemoryViewModel by viewModel()
    private val adapter:MemoryAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupList()
        setupEvents()
        viewModel.listMemory()
    }



    private fun setupEvents(){
        viewModel.memoryResult.observe(this){result -> onResultSuccess(result)}

    }

    private fun setupList(){
        binding.rvMemory.adapter=adapter
        binding.rvMemory.layoutManager=LinearLayoutManager(this)

}
    private fun onResultSuccess(memory:List<MemoryDto>){
        adapter.memory = memory
    }
}
