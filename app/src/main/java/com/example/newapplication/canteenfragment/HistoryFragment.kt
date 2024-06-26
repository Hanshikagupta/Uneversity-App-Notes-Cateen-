package com.example.newapplication.canteenfragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newapplication.adapter.BuyAgainAdapter

import com.example.newapplication.databinding.FragmentHistoryBinding
import com.example.newapplication.myapplication.RecentOrderItems

import com.example.newapplication.myapplication.model.OrderDetails

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter
    private lateinit var database :FirebaseDatabase
    private lateinit var auth : FirebaseAuth
    private lateinit var userId :String
    private var listOfOrderItem: ArrayList<OrderDetails> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        //retrive user order history
        retrieveBuyHistory()

        binding.recentBuyItem.setOnClickListener {
            seeItemRecentBuy()
        }
        binding.receivedbutton.setOnClickListener {
            updateOrderStatus()
        }
        return binding.root
    }

    private fun updateOrderStatus() {
        val itemPushKey =listOfOrderItem[0].itemPushKey
        val completeOrderRefernce =database.reference.child("CompletedOrder").child(itemPushKey!!)
        completeOrderRefernce.child("paymentReceived").setValue(true)
    }

    private fun seeItemRecentBuy() {
        listOfOrderItem.firstOrNull()?.let { recentBuy ->
            val intent = Intent(requireContext(), RecentOrderItems::class.java)
             intent.putExtra("RecentBuyOrderItem",listOfOrderItem)
              startActivity(intent)
        }
    }
    //to see item buy

//to retrive item history
    private fun retrieveBuyHistory() {
        binding.recentBuyItem.visibility=View.INVISIBLE
        userId= auth.currentUser?.uid?:""

        val buyItemReference :DatabaseReference= database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery =buyItemReference.orderByChild("currentTime")

        shortingQuery.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               for (buySnapshot in snapshot.children){
                   val buyHistoryItem=buySnapshot.getValue(OrderDetails::class.java)
                   buyHistoryItem?.let {
                       listOfOrderItem.add(it)
                   }
               }
                listOfOrderItem.reverse()
                if (listOfOrderItem.isNotEmpty())
                {
                    //display the most recent order detail
                    setDataInRecentBuyHistory()
                    //setup the recycler view with previous order detail
                    setPreviousBuyItemsRecyclerView()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setDataInRecentBuyHistory() {
        binding.recentBuyItem.visibility=View.VISIBLE
        val recentOrderItem = listOfOrderItem.firstOrNull()
        recentOrderItem?.let {
            with(binding){
                buyAgainFoodName.text=it.foodName?.firstOrNull()?:""
                buyAgainFoodPrize.text=it.foodPrices?.firstOrNull()?:""
                val image =it.foodImages?.firstOrNull()
                val uri= Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(buyAgainFoodImage)
                val isOrderAccepted= listOfOrderItem[0].orderAccepted
                if (isOrderAccepted){
                    orderStatus.background.setTint(Color.GREEN)
                    receivedbutton.visibility=View.VISIBLE
                }
                if (listOfOrderItem.isNotEmpty()){

                    }
                }
            }
        }

    private fun setPreviousBuyItemsRecyclerView() {
        val buyAgainFoodName = mutableListOf<String>()
        val buyAgainFoodPrize = mutableListOf<String>()
        val buyAgainFoodImage = mutableListOf<String>()
        for(i in 1 until listOfOrderItem.size)
        {
            listOfOrderItem[i].foodName?.firstOrNull()?.let { buyAgainFoodName.add(it)
            listOfOrderItem[i].foodPrices?.firstOrNull()?.let { buyAgainFoodPrize.add(it)
            listOfOrderItem[i].foodImages?.firstOrNull()?.let { buyAgainFoodImage.add(it) }
            }
            }
            val rv =binding.BuyAgainRecyclerView
            rv.layoutManager=LinearLayoutManager(requireContext())
            buyAgainAdapter=
                BuyAgainAdapter(buyAgainFoodName,buyAgainFoodPrize,buyAgainFoodImage,requireContext())
            rv.adapter=buyAgainAdapter
        }
}

}
