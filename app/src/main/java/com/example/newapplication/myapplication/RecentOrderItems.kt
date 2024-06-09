package com.example.newapplication.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.R
import com.example.newapplication.adapter.RecentBuyAdapter
import com.example.newapplication.databinding.ActivityRecentOrderItemsBinding
import com.example.newapplication.myapplication.model.OrderDetails

class RecentOrderItems : AppCompatActivity() {
    private val binding :ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodName:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.backButton.setOnClickListener {
            finish()
        }
        val recentOrderItems =intent.getSerializableExtra("RecentBuyOrderItem")as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty())
            {
                val recentOrderItem=orderDetails[0]
                allFoodName= recentOrderItem.foodName as ArrayList<String>
                allFoodImages= recentOrderItem.foodImages as ArrayList<String>
                allFoodPrices= recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities= recentOrderItem.foodQuantities as ArrayList<Int>
            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        val rv =binding.recentRecyclerView
        rv.layoutManager= LinearLayoutManager(this)
        val adapter= RecentBuyAdapter(this,allFoodName,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter=adapter
    }
}



