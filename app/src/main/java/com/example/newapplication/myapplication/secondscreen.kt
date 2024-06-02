package com.example.newapplication.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.newapplication.databinding.ActivitySecondscreenBinding

class secondscreen : AppCompatActivity() {
    private val binding: ActivitySecondscreenBinding by lazy {
        ActivitySecondscreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.hbtubtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}