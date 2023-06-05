package com.aditd5.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aditd5.bwamov.R
import com.aditd5.bwamov.sign.signin.SignInActivity

class OnBoardingThreeActivity : AppCompatActivity() {

    private lateinit var btnContinue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        btnContinue = findViewById(R.id.btnContinueTe)

        btnContinue.setOnClickListener {
            startActivity(Intent(this@OnBoardingThreeActivity, SignInActivity::class.java))
            finishAffinity()
        }
    }
}