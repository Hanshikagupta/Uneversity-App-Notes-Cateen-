package com.example.newapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newapplication.R
import com.example.newapplication.databinding.FragmentProfilefragmentBinding
import com.example.newapplication.myapplication.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener



class profilefragment : Fragment() {

    private lateinit var binding: FragmentProfilefragmentBinding
   private val auth=FirebaseAuth.getInstance()
    private val database=FirebaseDatabase.getInstance()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfilefragmentBinding.inflate(inflater,container,false)
        setUserData()
        binding.apply {
        nameEditText.isEnabled= false
        branchEditText2.isEnabled=false
        collegeEdittext2.isEnabled=false
        phonenoedittext.isEnabled=false
        binding.editButton.setOnClickListener {


                nameEditText.isEnabled=!nameEditText.isEnabled
                branchEditText2.isEnabled=!branchEditText2.isEnabled
                collegeEdittext2.isEnabled=!collegeEdittext2.isEnabled
                phonenoedittext.isEnabled=!phonenoedittext.isEnabled

            }
        }
        binding.savechanges.setOnClickListener(){
            val name= binding.nameEditText.text.toString()
            val branch= binding.branchEditText2.text.toString()
            val college= binding.collegeEdittext2.text.toString()
            val phone =binding.phonenoedittext.text.toString()
            updateUserData(name,branch,college,phone)
        }
        return binding.root


    }

    private fun updateUserData(name: String, branch: String, college: String,phone: String) {
        val userId=auth.currentUser?.uid
        if (userId!=null){
            val userReference=database.getReference("user").child(userId)
            val userData= hashMapOf(
                "name" to name,
                "branch" to branch,
                "college" to college,
                "phone" to phone

            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(),"Profile Update succesfully",Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener{
                    Toast.makeText(requireContext(),"Profile Update not succesfully",Toast.LENGTH_SHORT).show()

                }
        }
    }

    private fun setUserData() {
        val userId= auth.currentUser?.uid
        if (userId!=null)
        {
            val userReference=database.getReference("user").child(userId)
            userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   if (snapshot.exists()){
                       val userProfile=snapshot.getValue(UserModel::class.java)
                       if (userProfile!=null)
                       {
                           binding.nameEditText.setText(userProfile.name)
                          binding.branchEditText2.setText(userProfile.branch)
                           binding.collegeEdittext2.setText(userProfile.college)
                           binding.phonenoedittext.setText(userProfile.phone)
                       }
                   }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }


}
