package com.example.newapplication.myapplication


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newapplication.R
import com.example.newapplication.canteenfragment.HomeFragment

import com.example.newapplication.databinding.FragmentCongratsBottomSheetBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CongratsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentCongratsBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCongratsBottomSheetBinding.inflate(layoutInflater,container,false)
        binding.goHome.setOnClickListener{
            val intent = Intent(requireContext(), HomeFragment::class.java)
            startActivity(intent)
           // navigateToHomeFragment()
        }


        return binding.root
    }
  //  fun navigateToHomeFragment() {
   //     val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
     //   fragmentTransaction.replace(R.id.fragment_container, HomeFragment()) // R.id.fragment_container is the id of your container
       // fragmentTransaction.addToBackStack(null)
        //fragmentTransaction.commit()
    }


