package com.example.newapplication.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri

import android.view.LayoutInflater


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newapplication.myapplication.DetailsActivity

import com.example.newapplication.databinding.MenuitemBinding
import com.example.newapplication.myapplication.model.MenuItem


class Menuadapter(private val menuItems: List<MenuItem>,
                  private val requireContext: Context)
    :RecyclerView.Adapter<Menuadapter.MenuViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding= MenuitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      return MenuViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
      holder.bind(position)
    }

    override fun getItemCount(): Int =menuItems.size

   inner  class MenuViewHolder (private val binding : MenuitemBinding):RecyclerView.ViewHolder(binding.root){
       init {
           binding.root.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   opendetailActivity(position)
                   // setonclick listner to oopen details


               }

           }
       }

       private fun opendetailActivity(position: Int) {
           val menuItem=menuItems[position]

           // a intent
           val intent =Intent(requireContext, DetailsActivity::class.java).apply{
               putExtra("MenuItemName",menuItem.foodName)
               putExtra("MenuItemPrice",menuItem.foodPrice)
               putExtra("MenuItemDescription",menuItem.foodDescription)
               putExtra("MenuItemImage",menuItem.foodImage)
               putExtra("MenuItemIngredient",menuItem.foodIngredient)

           }
           //start the detail activity
           requireContext.startActivity(intent)
       }

       //data in recycler view item

       fun bind(position: Int) {
           val menuItem =menuItems[position]
           binding.apply {
               menufoodname.text=menuItem.foodName
               menuprice.text=menuItem.foodPrice

               val uri = Uri.parse(menuItem.foodImage)
               Glide.with(requireContext).load(uri).into(menuimg)



           }

       }

   }



}
