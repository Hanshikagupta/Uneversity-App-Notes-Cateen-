package com.example.newapplication.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.newapplication.R
import com.example.newapplication.databinding.FragmentCanteenfragmentBinding


import com.example.newapplication.myapplication.canteenstart


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class canteenfragment  : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentCanteenfragmentBinding

    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)



        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=   FragmentCanteenfragmentBinding.inflate(inflater,container,false)



         return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.img_2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img_1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img_2, ScaleTypes.FIT))
        val imageSlider= binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage, Toast.LENGTH_SHORT).show()
            }

        })


        val cardView: CardView = view.findViewById(R.id.cardstep)

        // Set OnClickListener for the CardView
        cardView.setOnClickListener {
            // Implement the navigation logic here
            val intent = Intent(requireActivity(), canteenstart::class.java)
            startActivity(intent)
        }



    }

    companion object {

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.cardstep) {
            // Start the new activity
            val intent = Intent(context, canteenstart::class.java)
            startActivity(intent)
        }

    }
}