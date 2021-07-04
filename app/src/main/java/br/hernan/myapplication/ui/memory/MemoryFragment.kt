package br.hernan.myapplication.ui.memory

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.hernan.myapplication.R
import br.hernan.myapplication.databinding.FragmentMemoryBinding
import br.hernan.myapplication.domain.dto.MemoryDto
import br.hernan.myapplication.ui.newmemory.ActivityNewMemory
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

        setHasOptionsMenu(true)
        setupList()
        setupEvents()
        viewModel.listMemory()
    }

    override fun onCreateOptionsMenu(menu: Menu,inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnAdd -> {
                startActivity(ActivityNewMemory.newIntent(requireContext())) } }
        return true }



    private fun setupEvents(){
        viewModel.memoryResult.observe(this){result -> onResultSuccess(result)}

    }

    private fun setupList(){
        binding.rvMemory.adapter=adapter
        binding.rvMemory.layoutManager=LinearLayoutManager(requireContext())

}
    private fun onResultSuccess(memory:List<MemoryDto>){
        adapter.memory = memory
    }
}
