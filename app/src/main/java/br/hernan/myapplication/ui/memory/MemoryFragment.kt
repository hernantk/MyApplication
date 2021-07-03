package br.hernan.myapplication.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.hernan.myapplication.databinding.FragmentMemoryBinding
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
    }
}