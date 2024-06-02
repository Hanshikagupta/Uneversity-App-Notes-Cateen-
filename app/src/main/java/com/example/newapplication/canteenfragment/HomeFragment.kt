package com.example.newapplication.canteenfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.newapplication.R
import com.example.newapplication.adapter.Menuadapter


import com.example.newapplication.databinding.FragmentHomeBinding
import com.example.newapplication.myapplication.menubottomsheetFragment
import com.example.newapplication.myapplication.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)

        binding.viewmenucanteen.setOnClickListener{
            val bottomSheetDialog = menubottomsheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")

        }

        retriveDisplayPoplarItem()

        return binding.root


    }
    private fun retriveDisplayPoplarItem() {//get refernce to database
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference=database.reference.child("menu")
        menuItems = mutableListOf()
        //retrive menuitem
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(MenuItem::class.java)
                    menuItem.let {
                        if (it != null) menuItems.add(it)
                    }
                }
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun randomPopularItems() {
        //suffeled list is created
        val index= menuItems.indices.toList().shuffled()
        val menuItemstoshow=3
        val subsetMenuItems =index.take(menuItemstoshow).map { menuItems[it] }
        setPopularItemAdapter(subsetMenuItems)

    }
    private fun setPopularItemAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter= Menuadapter(subsetMenuItems,requireContext())
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList2 = ArrayList<SlideModel>()
        imageList2.add(SlideModel(R.drawable.img_2, ScaleTypes.FIT))
        imageList2.add(SlideModel(R.drawable.img_3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider3
        imageSlider.setImageList(imageList2)
        imageSlider.setImageList(imageList2, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList2[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }


}