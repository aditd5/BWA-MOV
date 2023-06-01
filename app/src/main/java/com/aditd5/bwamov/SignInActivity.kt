package com.aditd5.bwamov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var btnSignin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignin = findViewById(R.id.btnSignin)

        btnSignin.setOnClickListener {
            // Write a message to the database
            val database = Firebase.database("https://bwa-mov-aditd5-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val myRef = database.getReference("message")

            myRef.setValue("Hello, World!")
        }


    }
}