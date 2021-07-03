package br.hernan.myapplication.app

import android.app.Application
import br.hernan.myapplication.di.adapterModule
import br.hernan.myapplication.di.repositoryModule
import br.hernan.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MemoryApp:Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(applicationContext)
            modules(
                viewModelModule,
                repositoryModule,
                adapterModule

            )
        }
    }


}