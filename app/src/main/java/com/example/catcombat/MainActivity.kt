package com.example.catcombat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.catcombat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .also {
                setContentView(it.root)
            }
        binding.imageButton.setOnClickListener {
            viewModel.increasePoints()
        }

        viewModel.initState(
            savedInstanceState?.getParcelable(K_ST)
                ?: MainActivityViewModel.State(pointCounter = 0)
        )

        viewModel.state.observe(this, Observer {
            binding.pointsCounter.text = viewModel.state.value!!.pointCounter.toString()
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(K_ST, viewModel.state.value)
    }

    companion object {
        @JvmStatic val K_ST = "KEY STATE"
    }
}