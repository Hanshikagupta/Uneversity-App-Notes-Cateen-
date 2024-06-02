package com.example.newapplication.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.R
import com.example.newapplication.adapter.BuyAgainAdapter
import com.example.newapplication.adapter.RecentBuyAdapter
import com.example.newapplication.databinding.ActivityRecentItemsBinding
import com.example.newapplication.myapplication.model.OrderDetails

class RecentItems : AppCompatActivity() {
    private val binding :ActivityRecentItemsBinding by lazy {
        ActivityRecentItemsBinding.inflate(layoutInflater)
    }
    private lateinit var allFoodName:ArrayList<String>
    private lateinit var allFoodImages:ArrayList<String>
    private lateinit var allFoodPrices:ArrayList<String>
    private lateinit var allFoodQuantities:ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recent_items)

        val recentItems
         =intent.getSerializableExtra("RecentBuyOrderItem")as ArrayList<OrderDetails>
        recentItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty())
            {
                val recentItem=orderDetails[0]
                allFoodName= recentItem.foodName as ArrayList<String>
                allFoodImages= recentItem.foodImages as ArrayList<String>
                allFoodPrices= recentItem.foodPrices as ArrayList<String>
                allFoodQuantities= recentItem.foodQuantities as ArrayList<Int>
            }
        }
        setAdapter()
        }

    private fun setAdapter() {
        val rv =binding.recentRecyclerView
        rv.layoutManager= LinearLayoutManager(this)
        val adapter=RecentBuyAdapter(this,allFoodName,allFoodImages,allFoodPrices,allFoodQuantities)
        rv.adapter=adapter
    }
}
