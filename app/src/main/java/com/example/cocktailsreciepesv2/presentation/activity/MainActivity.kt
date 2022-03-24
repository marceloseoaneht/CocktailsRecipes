package com.example.cocktailsreciepesv2.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cocktailsreciepesv2.databinding.ActivityMainBinding
import com.example.cocktailsreciepesv2.presentation.fragment.DrinkInfoFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}