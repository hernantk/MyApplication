package br.hernan.myapplication.di

import android.net.Uri
import br.hernan.myapplication.domain.repository.MemoryRepository
import br.hernan.myapplication.ui.maps.MapsViewModel
import br.hernan.myapplication.ui.memory.MemoryAdapter
import br.hernan.myapplication.ui.memory.MemoryViewModel
import br.hernan.myapplication.ui.newmemory.ViewModelNewMemory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel{MemoryViewModel(get())}
    viewModel{MapsViewModel(get())}
    viewModel{ViewModelNewMemory(get()) }
}

val repositoryModule = module {
    single { MemoryRepository(FirebaseFirestore.getInstance()) }
}

val adapterModule = module {
    factory { MemoryAdapter() }
}




