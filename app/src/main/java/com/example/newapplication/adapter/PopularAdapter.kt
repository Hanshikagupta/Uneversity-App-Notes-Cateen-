package com.example.newapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newapplication.myapplication.DetailsActivity

import com.example.newapplication.databinding.PopularitemBinding

class PopularAdapter(private val items:List<String>,private val image:List<Int>,private val prize :List<String> , private val requireContext : Context):RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item= items[position]
        val images= image[position]
        val prize=prize[position]
        holder.bind(item,prize,images)

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName",item )
            intent.putExtra("MenuItemImage", images )
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }

    class PopularViewHolder(private val binding : PopularitemBinding):RecyclerView.ViewHolder(binding.root) {
       private val imagesView= binding.foodimg
        fun bind(item: String,prize: String, images: Int) {
           binding.foodname.text=item

          binding.price  .text=prize
          imagesView.setImageResource(images)
        }

    }
}