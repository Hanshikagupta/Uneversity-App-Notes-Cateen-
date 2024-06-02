package com.example.newapplication.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class canteenstart : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_canteenstart)

        var NavController=findNavController(R.id.fragmentContainerView2)
        var bottomNavCanteen=findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNavCanteen.setupWithNavController(NavController)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}