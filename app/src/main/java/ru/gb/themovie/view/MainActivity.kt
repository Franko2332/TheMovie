package ru.gb.themovie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gb.themovie.R
import ru.gb.themovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}