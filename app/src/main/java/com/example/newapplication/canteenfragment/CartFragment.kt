package com.example.newapplication.canteenfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.adapter.Cartadapter
import com.example.newapplication.myapplication.PayOutActivity

import com.example.newapplication.R
import com.example.newapplication.databinding.FragmentCartBinding
import com.example.newapplication.myapplication.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName: MutableList<String>
    private lateinit var foodPrice: MutableList<String>
    private lateinit var foodDescription: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngradient: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartadapter: Cartadapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        //initiale
        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.proceedButton.setOnClickListener {
            //get order itm detail
            getOrderItemDetail()
            val intent=Intent(requireContext(),PayOutActivity::class.java)
          startActivity(intent)

        }



        return binding.root
    }

    private fun getOrderItemDetail() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngradient = mutableListOf<String>()
        //get item quantity
        val foodQuantities = cartadapter.getUpdatedItemsQuantities()
        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get item to respective list
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    //add item detail to list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngradient?.let { foodIngradient.add(it) }
                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImage,
                    foodIngradient,
                    foodQuantities
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Try Again Later!!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngradient: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {
        if (isAdded && context != null) {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage", foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescription", foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngradient", foodIngradient as ArrayList<String>)
            intent.putExtra("FoodItemQuantities", foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }
    }

    private fun retrieveCartItems() {
        //database refernce to firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        //store variable in ;ist
        foodName = mutableListOf()
        foodPrice = mutableListOf()
        foodDescription = mutableListOf()
        foodImageUri = mutableListOf()
        foodIngradient = mutableListOf()
        quantity = mutableListOf()
        //fetch data from database

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get cart item obeject from chid node
                    val cartItems = foodSnapshot.getValue(CartItems::class.java)
//add cart item detail to list
                    cartItems?.foodName?.let { foodName.add(it) }
                    cartItems?.foodPrice?.let { foodPrice.add(it) }
                    cartItems?.foodDescription?.let { foodDescription.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngradient?.let { foodIngradient.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartadapter = Cartadapter(
                    requireContext(),
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImageUri,
                    quantity,
                    foodIngradient
                )
                binding.recyclerView2.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.recyclerView2.adapter = cartadapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetched", Toast.LENGTH_SHORT)
            }

        })
    }


    companion object {


    }
}