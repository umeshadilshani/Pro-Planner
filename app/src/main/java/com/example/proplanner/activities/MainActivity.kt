//IT21313684
//Karunarathna D.T.S
package com.example.proplanner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proplanner.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {

    // Declare variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginbtn.setOnClickListener{
            val email = binding.loginEmail.text.toString()
            val password =  binding.loginPassword.text.toString()

            // Validate email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, MainDashboard::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                Toast.makeText(this,"Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        // Set onClickListener for sign up redirect text
        binding.signupRedirectText.setOnClickListener{
            val signupIntent = Intent(this, SignUp::class.java)
            startActivity(signupIntent)
        }
    }
}