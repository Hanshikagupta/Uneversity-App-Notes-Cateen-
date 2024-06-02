package com.example.newapplication.canteenfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater


import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapplication.adapter.Menuadapter


import com.example.newapplication.databinding.FragmentSearchBinding
import com.example.newapplication.myapplication.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var  adapter : Menuadapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems= mutableListOf<MenuItem>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSearchBinding.inflate(inflater,container,false)
      //retrive menu item from database
        retriveMenuItem()
        // set up for search view
        setupSearchView()


        return binding.root
    }

    private fun retriveMenuItem() {
        //get databse reference
        database= FirebaseDatabase.getInstance()
        //referce to menu node
        val foodReference: DatabaseReference=database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem= foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun showAllMenu() {
        val filtertedMenuItem=ArrayList(originalMenuItems)
        setAdapter(filtertedMenuItem)
    }

    private fun setAdapter(filtertedMenuItem: List<MenuItem>) {
        adapter= Menuadapter(filtertedMenuItem,requireContext())
        binding.searchrecyclerviewbottom.layoutManager=LinearLayoutManager(requireContext())
        binding.searchrecyclerviewbottom.adapter=adapter
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }


        })

    }
    private fun filterMenuItems(query: String) {
         val filtertedMenuItems= originalMenuItems.filter {
             it.foodName?.contains(query, ignoreCase = true)==true
         }
        setAdapter(filtertedMenuItems)
        }

    companion object {


    }

    }






