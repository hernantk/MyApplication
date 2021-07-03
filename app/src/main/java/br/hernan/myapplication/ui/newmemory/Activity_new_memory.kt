package br.hernan.myapplication.ui.newmemory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.hernan.myapplication.R

class ActivityNewMemory : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_memory)
    }


    companion object {
        fun newIntent(context: Context) = Intent(context, ActivityNewMemory::class.java)
    }
}