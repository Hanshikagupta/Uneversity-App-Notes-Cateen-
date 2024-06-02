package com.example.newapplication.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.newapplication.databinding.ActivityFirstscreenBinding

class firstscreen : AppCompatActivity() {
    private val binding: ActivityFirstscreenBinding by lazy {
        ActivityFirstscreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.universitybtn.setOnClickListener{
            val intent =Intent(this, secondscreen::class.java)
            startActivity(intent)

        }
    }
}