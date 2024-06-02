package com.example.newapplication.adapter

import android.content.Context
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.newapplication.databinding.CartitemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Cartadapter(private  val context: Context,
                  private val cartItems:MutableList<String>,
                  private val cartItemPrice : MutableList<String>,
                  private val cartDescription :MutableList<String>,
                  private var cartImage :MutableList<String>,
                  private val cartQuantity :MutableList<Int>,
                  private val cartIngradient :MutableList<String>

    ):RecyclerView.Adapter<Cartadapter.CartViewHolder>() {
        //instance firebase
        private val auth= FirebaseAuth.getInstance()

    init {
        val database=FirebaseDatabase.getInstance()
        val userId =auth.currentUser?.uid?:""
        val cartItemNumber=cartItems.size
        itemQuantities= IntArray(cartItemNumber){1}
        cartItemReference=database.reference.child("user").child(userId).child("CartItems")
    }


      companion object{
          private var itemQuantities :IntArray= intArrayOf()
        private lateinit var cartItemReference: DatabaseReference

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }



    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int= cartItems.size
    fun getUpdatedItemsQuantities(): MutableList<Int> {
        val itemQuantity= mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity

    }

    inner class CartViewHolder(private val binding: CartitemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity =itemQuantities[position]
                cartfoodname.text=cartItems[position]
                cartitemprize.text=cartItemPrice[position]

                //load image using glide
                val uriString = cartImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartimage)
                cartItemQuantity.text=quantity.toString()

                minusbutton.setOnClickListener{
                    decreaseQuantity(position)
                }
                plusbutton.setOnClickListener{
                    increaseQuantity(position)
                }
                delete.setOnClickListener {

                 val itemposition =adapterPosition
                 if(itemposition!=RecyclerView.NO_POSITION){
                     deleteQuantity(itemposition)
                 }
                }

            }


        }
       private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                cartQuantity[position]= itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
       private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                cartQuantity[position]= itemQuantities[position]
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteQuantity(position: Int){
           val positionRetrieve =position
            getUniqueKeyAtPosition(positionRetrieve){uniqueKey->
                if(uniqueKey!=null)
                {
                    removeItem(position,uniqueKey)
                }
            }

        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey!=null)
            {
                cartItemReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImage.removeAt(position)
                    cartDescription.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartItemPrice.removeAt(position)
                    cartIngradient.removeAt(position)
                    Toast.makeText(context,"Item deleted",Toast.LENGTH_SHORT).show()
                    //update item quantity
                    itemQuantities= itemQuantities.filterIndexed{ index,i ->index!= position }.toIntArray()
               notifyItemRemoved(position)
                    notifyItemRangeChanged(position,cartItems.size)
                }.addOnFailureListener {
                    Toast.makeText(context,"failed to delete",Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun getUniqueKeyAtPosition(positionRetrieve: Int,onComplete:(String?)->Unit) {
            cartItemReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var uniqueKey:String?=null
                    snapshot.children.forEachIndexed{
                        index,dataSnapshot->
                        if (index== positionRetrieve){
                            uniqueKey=dataSnapshot.key
                            return@forEachIndexed
                        }
                    }
                    onComplete(uniqueKey)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }


}