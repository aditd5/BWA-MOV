package com.aditd5.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aditd5.bwamov.R
import com.aditd5.bwamov.SignInActivity
import com.aditd5.bwamov.utils.Preferences

class OnBoardingOneActivity : AppCompatActivity() {

    private lateinit var btnContinue: Button
    private lateinit var btnSkip: Button
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        btnContinue = findViewById(R.id.btnContinue)
        btnSkip = findViewById(R.id.btnSkip)
        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")) {
            var intent = Intent(this@OnBoardingOneActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        btnSkip.setOnClickListener {
            preferences.setValues("onboarding","1")
            finishAffinity()

            var intent = Intent(this@OnBoardingOneActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            var intent = Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java)
            startActivity(intent)
        }
    }
}