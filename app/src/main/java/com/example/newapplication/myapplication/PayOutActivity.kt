package com.example.newapplication.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.newapplication.R
import com.example.newapplication.databinding.ActivityPayOutBinding
import com.example.newapplication.myapplication.model.OrderDetails

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class PayOutActivity : AppCompatActivity() {
     private lateinit var binding: ActivityPayOutBinding
     private lateinit var auth:FirebaseAuth
     private lateinit var name:String
     private lateinit var tableno:String
     private lateinit var phone :String
     private lateinit var total:String
     private lateinit var foodItemName:ArrayList<String>
     private lateinit var foodItemPrice:ArrayList<String>
     private lateinit var foodItemImage:ArrayList<String>
     private lateinit var foodItemDescription:ArrayList<String>
     private lateinit var foodItemIngradient:ArrayList<String>
     private lateinit var foodItemQuantities:ArrayList<Int>
     private lateinit var databaseReference: DatabaseReference
     private lateinit var userId :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InilizeFirebase and user details
        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference()

        //set user data
        setUserData()

        //get user details from firebase
        val intent=intent
        foodItemName=intent.getStringArrayListExtra("FoodItemName")?: ArrayList()

        foodItemPrice=intent.getStringArrayListExtra("FoodItemPrice")?: ArrayList()
        foodItemImage=intent.getStringArrayListExtra("FoodItemImage")?: ArrayList()
        foodItemDescription=intent.getStringArrayListExtra("FoodItemDescription")?: ArrayList()
        foodItemIngradient=intent.getStringArrayListExtra("FoodItemIngradient")?: ArrayList()
        foodItemQuantities=intent.getIntegerArrayListExtra("FoodItemQuantities")?: ArrayList()

        total =calculateTotalAmount().toString()+"$"

        binding.totalamount.setText(total)
        binding.backbutton.setOnClickListener{
            finish()
        }

        binding.placeMyOrder.setOnClickListener {
            //get data from text view
           name=binding.name.text.toString().trim()
           tableno=binding.tableno.text.toString().trim()
           phone=binding.phoneno.text.toString().trim()
            if (name.isBlank()&&tableno.isBlank()&&phone.isBlank())
            {
                Toast.makeText(this, "fill all detail",Toast.LENGTH_SHORT).show()
            }
            else{
                placeOrder()
            }

        }
        }

    private fun placeOrder() {
        userId=auth.currentUser?.uid?:""
        val time =System.currentTimeMillis()
        val itemPushKey =databaseReference.child("OrderDetails").push().key
        val orderDetails=OrderDetails(userId,name,foodItemName,foodItemPrice,foodItemImage,foodItemQuantities,tableno,total,phone,time,itemPushKey,false,false)
        val orderReference=databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener{
                Toast.makeText(this,"Failed To Order",Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemReference =databaseReference.child("user").child(userId).child("CartItems")
        cartItemReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
      var totalAmount=0
        for (i in 0 until foodItemPrice.size){
            var price= foodItemPrice[i]
            val lastchar= price.last()
            val priceIntValue = if (lastchar == '$')
            {
                price.dropLast(1).toInt()
            }
            else{
                price.toInt()
            }
            var quantity =foodItemQuantities[i]
            totalAmount +=priceIntValue*quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user =auth.currentUser
        if (user!=null)
        {
         val userId=user.uid
         val userReferance =databaseReference.child("user").child(userId)

         userReferance.addListenerForSingleValueEvent(object :ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                 if(snapshot.exists())
                 {
                     val names =snapshot.child("name").getValue(String::class.java)?:""
                     val tablenos=snapshot.child("tablenos").getValue(String::class.java)?:""
                     val phones=snapshot.child("phone").getValue(String::class.java)?:""
                     binding.apply {
                         name.setText(names)
                         tableno.setText(tablenos)
                         phoneno.setText(phones)
                     }
                 }

             }

             override fun onCancelled(error: DatabaseError) {

             }

         })
        }

    }


}


