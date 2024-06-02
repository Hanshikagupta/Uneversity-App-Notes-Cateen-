package com.example.newapplication.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.newapplication.databinding.BuyAgainItemBinding


class BuyAgainAdapter(private val buyAgainFoodName: MutableList<String>,
                      private val buyAgainFoodPrize:MutableList<String>,
                      private val buyAgainFoodImage: MutableList<String>,
                      private val requireContext: Context

) :
    RecyclerView.Adapter<BuyAgainAdapter.BuyAgainViewHolder>() {

    override fun onBindViewHolder(holder: BuyAgainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position],buyAgainFoodPrize[position],buyAgainFoodImage[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyAgainViewHolder {
       val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BuyAgainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size


    inner class BuyAgainViewHolder (private val binding: BuyAgainItemBinding):RecyclerView.ViewHolder
        (binding.root){
        fun bind(foodName: String, foodPrize: String, foodImage: String) {
            binding.buyAgainFoodName.text=foodName
            binding.buyAgainFoodPrize.text=foodPrize
           val uriString =foodImage
            val uri= Uri.parse(uriString)
            Glide.with(requireContext).load(uri).into(binding.buyAgainFoodImage)
            }
        }

    }



