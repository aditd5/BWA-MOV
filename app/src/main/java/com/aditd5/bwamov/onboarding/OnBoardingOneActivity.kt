package com.aditd5.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aditd5.bwamov.R
import com.aditd5.bwamov.SignInActivity

class OnBoardingOneActivity : AppCompatActivity() {

    private lateinit var btnContinue: Button
    private lateinit var btnSkip: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        btnContinue = findViewById(R.id.btnContinue)
        btnSkip = findViewById(R.id.btnSkip)

        btnSkip.setOnClickListener {
            var intent = Intent(this@OnBoardingOneActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            var intent = Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java)
            startActivity(intent)
        }
    }
}