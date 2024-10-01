package com.example.catcombat

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.catcombat.databinding.ActivityMainBinding
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    private var startX = 0f
    private var startY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        binding.imageButton.setOnClickListener {
            viewModel.increasePoints(1) // Increment by click
        }


        // Set up swipe listener
        binding.imageButton.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.x
                    startY = event.y
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val endX = event.x
                    val endY = event.y
                    val deltaX = endX - startX
                    val deltaY = endY - startY

                    if (abs(deltaX) > abs(deltaY) && abs(deltaX) > 100) { // Horizontal swipe
                        if (deltaX > 0) {
                           viewModel.increasePoints(5) // swipe right, add 5 points
                        } else {
                           viewModel.increasePoints(3) // swipe left, add 3 points
                        }
                    } else if (abs(deltaY) > abs(deltaX) && abs(deltaY) > 100) { // Vertical swipe
                        if (deltaY > 0) {
                            viewModel.increasePoints(2) // swipe down, add 2 points

                        } else {
                            viewModel.increasePoints(7) // swipe up, add 7 points
                        }
                    }
                    true
                }
                else -> false
            }
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
        @JvmStatic
        val K_ST = "KEY STATE"
    }
}
