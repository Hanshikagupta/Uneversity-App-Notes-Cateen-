package com.example.newapplication.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.newapplication.R
import com.example.newapplication.databinding.FragmentHomefragmentBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class homefragment : Fragment() {
    private lateinit var binding : FragmentHomefragmentBinding

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
        // Inflate the layout for this fragment

         binding=   FragmentHomefragmentBinding.inflate(inflater,container,false)
        binding.textView17.setOnClickListener{
            openUrl("https://btechgeeks.com/b-tech-first-year-notes")
        }

        return binding.root

    }
    private fun openUrl(link: String) {
        val uri = Uri.parse(link)
        val inte= Intent(Intent.ACTION_VIEW,uri)
        startActivity(inte)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.istock,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.istock,ScaleTypes.FIT))
        val imageSlider= binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList,ScaleTypes.FIT)
        imageSlider.setItemClickListener(object :ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
               val itemPosition = imageList[position]
                val itemMessage="Selected Image $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()
            }

        })

    }
    companion object {



    }
}