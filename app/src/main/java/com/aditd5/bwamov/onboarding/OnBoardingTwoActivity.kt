package com.aditd5.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aditd5.bwamov.R

class OnBoardingTwoActivity : AppCompatActivity() {

    private lateinit var btnContinue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btnContinue = findViewById(R.id.btnContinueTo)

        btnContinue.setOnClickListener {
            startActivity(Intent(this@OnBoardingTwoActivity,OnBoardingThreeActivity::class.java))
        }
    }
}