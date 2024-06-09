package com.example.newapplication.myapplication

import android.net.Uri
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import com.example.newapplication.databinding.ActivityDetailsBinding
import com.example.newapplication.myapplication.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailsBinding
    private var foodName :String?=null
    private var foodImage :String?=null
    private var foodDescription :String?=null
    private var foodIngradient:String?=null
    private var foodPrice :String?=null
    private  lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        foodName= intent.getStringExtra("MenuItemName")
        foodIngradient=intent.getStringExtra("MenuItemIngredient")
        foodPrice=intent.getStringExtra("MenuItemPrice")
        foodDescription=intent.getStringExtra("MenuItemDescription")
        foodImage=intent.getStringExtra("MenuItemImage")

        with(binding){
            detailFoodName.text=foodName
            detailDescription.text=foodDescription
            detailingredients.text=foodIngradient
            Glide.with(this@DetailsActivity).load(Uri.parse(foodImage)).into(detailFoodImage)
            
        }

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.detailaddtocartbutton.setOnClickListener {
            addItemToCart()
            finish()
        }
        }

    private fun addItemToCart() {
        val database =FirebaseDatabase.getInstance().reference
        val userId=auth.currentUser?.uid?:""
        // create a cart item object
        val cartItem =CartItems(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(), 1,foodIngradient.toString())

        //save item to cart
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Items added to cart",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Items  not added to cart",Toast.LENGTH_SHORT).show()
        }
    }
}
