package dev.sergiobelda.todometer.wear

import android.app.Activity
import android.os.Bundle
import dev.sergiobelda.todometer.wear.databinding.MainActivityBinding

class MainActivity : Activity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
