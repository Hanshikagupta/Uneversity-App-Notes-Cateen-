package com.example.newapplication.myapplication

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import com.example.newapplication.myapplication.model.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.adapter.Menuadapter



import com.example.newapplication.databinding.FragmentMenubottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class menubottomsheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenubottomsheetBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenubottomsheetBinding.inflate(inflater, container, false)

        binding.buttonback.setOnClickListener{
            dismiss()
        }
        retriveMenuItems()

        return binding.root

    }

    private fun retriveMenuItems() {
        database=FirebaseDatabase.getInstance()
        val foodref :DatabaseReference= database.reference.child("menu")
        menuItems= mutableListOf()

        foodref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children ){
                    val menuItem= foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it)  }

                }
                Log.d("ITEMS","OnDataChange: data recevied")

                //set to adapter
                setAdapter()

            }


            override fun onCancelled(error: DatabaseError) {
                Log.e("DATABASE", "Failed to retrieve menu items: ${error.message}")
            }


        })

    }
    private fun setAdapter() {
        if (isAdded) {
            if (menuItems.isNotEmpty()) {
                val adapter = Menuadapter(menuItems, requireContext())
                binding.menurecyclerviewbottom.layoutManager = LinearLayoutManager(requireContext())
                binding.menurecyclerviewbottom.adapter = adapter
                Log.d("ITEMS", "setAdapter: data set")
            } else {
                Log.d("ITEMS", "setAdapter: data  not set")

            }

        }
        else{
            Log.d("FRAGMENT", "fragment not attached to a contex")
        }
    }



    companion object{



      }

}
