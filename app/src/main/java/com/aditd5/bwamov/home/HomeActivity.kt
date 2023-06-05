package com.aditd5.bwamov.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.aditd5.bwamov.R

class HomeActivity : AppCompatActivity() {

    private lateinit var btnHome: ImageView
    private lateinit var btnTicket: ImageView
    private lateinit var btnProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnHome = findViewById(R.id.btnHome)
        btnTicket = findViewById(R.id.btnTicket)
        btnProfile = findViewById(R.id.btnProfile)

        val fragmentHome = HomeFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentHome)

        btnHome.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(btnHome,R.drawable.ic_home_active)
            changeIcon(btnTicket,R.drawable.ic_ticket)
            changeIcon(btnProfile,R.drawable.ic_profile)
        }

        btnTicket.setOnClickListener {
            setFragment(fragmentTicket)

            changeIcon(btnHome,R.drawable.ic_home)
            changeIcon(btnTicket,R.drawable.ic_ticket_active)
            changeIcon(btnProfile,R.drawable.ic_profile)
        }

        btnProfile.setOnClickListener {
            setFragment(fragmentProfile)

            changeIcon(btnHome,R.drawable.ic_home)
            changeIcon(btnTicket,R.drawable.ic_ticket)
            changeIcon(btnProfile,R.drawable.ic_profile_active)
        }
    }
    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame,fragment)
        fragmentTransaction.commit()
    }
    private fun changeIcon(imageView: ImageView,int: Int) {
        imageView.setImageResource(int)
    }
}
