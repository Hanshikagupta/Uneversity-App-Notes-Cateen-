package com.example.newapplication.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.example.newapplication.databinding.ActivitySigninBinding
import com.example.newapplication.myapplication.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email :String
    private lateinit var password: String
    private lateinit var name :String
    private lateinit var database :DatabaseReference
    //private lateinit var googleSignInClient:GoogleSignInClient



    private val binding: ActivitySigninBinding by lazy {
    ActivitySigninBinding.inflate(layoutInflater)

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//val googleSignInOption= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
   // .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        //Firebase
        auth = Firebase.auth
        database =Firebase.database.reference
//google sign in
      //  googleSignInClient= GoogleSignIn.getClient(this,)

        binding.createbutton.setOnClickListener{
            //get text from edit text
            email=binding.emailsign.text.toString().trim()
            name=binding.namesign.text.toString().trim()
            password=binding.passwordsign.text.toString().trim()

            if(email.isBlank()||password.isBlank()||name.isBlank())
            {
                Toast.makeText(this,"Please fill all detail",Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }


        }
        binding.alreadybutton.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
       auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
           if(task.isSuccessful){
               Toast.makeText(this,"Sucessfully registered",Toast.LENGTH_SHORT).show()
               saveUserData()
               val intent= Intent(this, LoginActivity::class.java)
               startActivity(intent)
               finish()
           }
           else
           {
               Toast.makeText(this,"registration failed",Toast.LENGTH_SHORT).show()
               Log.d("Account","createaccount exception",task.exception)
           }
       }

    }
//sav data in base
    private fun saveUserData() {
        name=binding.namesign.text.toString().trim()
        val user = UserModel(name)
        val userId:String =FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}