package com.example.newapplication.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.newapplication.databinding.ActivityThirdscreenBinding

class thirdscreen : AppCompatActivity() {
    private lateinit var binding: ActivityThirdscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityThirdscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView16.setOnClickListener{
            openUrl("https://www.youtube.com")
        }
    }

    private fun openUrl(link: String) {
       val uri = Uri.parse(link)
        val inte= Intent(Intent.ACTION_VIEW,uri)
        startActivity(inte)
    }
}