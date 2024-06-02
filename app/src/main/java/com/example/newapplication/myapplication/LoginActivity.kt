package com.example.newapplication.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.example.newapplication.databinding.ActivityLoginBinding
import com.example.newapplication.myapplication.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val binding : ActivityLoginBinding by lazy {
      ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        database = Firebase.database.reference
        binding.loginbutton.setOnClickListener{
            email=binding.emaillogin.text.toString().trim()

            password=binding.passwordlogin.text.toString().trim()

            if(email.isBlank()||password.isBlank())
            {
                Toast.makeText(this,"Please fill all detail", Toast.LENGTH_SHORT).show()
            }
            else{
          createUserAccount(email,password)
        }
        binding.dontbutton.setOnClickListener{
            val intent=Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}

    private fun createUserAccount(email: String, password: String) {
       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
           if(task.isSuccessful)
           {
               val user :FirebaseUser?=auth.currentUser
               updateUi(user)

           }
           else{
               auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
                   if(task.isSuccessful)
                   {
                       val user :FirebaseUser?=auth.currentUser
                       saveUserData()
                       updateUi(user)
                   }
                    else{
                       Toast.makeText(this,"Authentication Failed", Toast.LENGTH_SHORT).show()
                       Log.d("Account","createUserAccount: Authentication failed ",task.exception)

                   }
               }
           }
       }
    }

    private fun saveUserData() {
        email=binding.emaillogin.text.toString().trim()
        password=binding.passwordlogin.text.toString().trim()
        val user= UserModel(email,password)
        val userId: String?=FirebaseAuth.getInstance().currentUser?.uid
        userId?.let{

                database.child("user").child(it).setValue(user)

        }
    }
    // on start
    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser!= null)
        {
            val intent=Intent(this, firstscreen::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun updateUi(user: FirebaseUser?) {
        val intent=Intent(this, firstscreen::class.java)
        startActivity(intent)
        finish()
    }
}



