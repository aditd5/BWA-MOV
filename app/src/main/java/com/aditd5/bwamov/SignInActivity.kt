package com.aditd5.bwamov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aditd5.bwamov.utils.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var btnSignin: Button
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSignin = findViewById(R.id.btnSignin)
        btnSignup = findViewById(R.id.btnSignupSI)
        etUsername = findViewById(R.id.etUsernameSI)
        etPassword = findViewById(R.id.etPasswordSI)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValues("onboarding", "1")
        if (preferences.getValues("status").equals("1")){
            finishAffinity()

            var goHome = Intent(this@SignInActivity,HomeActivity::class.java)
            startActivity(goHome)
        }

        btnSignin.setOnClickListener {
            val iUsername = etUsername.text.toString()
            val iPassword = etPassword.text.toString()

            if (iUsername.equals("")) {
                etUsername.error = "Silahkan masukkan username anda"
                etUsername.requestFocus()
            } else if (iPassword.equals("")) {
                etPassword.error = "Silahkan masukkan password anda"
                etPassword.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        btnSignup.setOnClickListener {
            var signup = Intent(this@SignInActivity,SignUpActivity::class.java)
            startActivity(signup)
        }
    }

    private fun pushLogin(iUsername : String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity,"Username tidak ditemukan",Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(iPassword)) {

                        preferences.setValues("nama",user.nama.toString())
                        preferences.setValues("user",user.username.toString())
                        preferences.setValues("url",user.url.toString())
                        preferences.setValues("email",user.email.toString())
                        preferences.setValues("saldo",user.saldo.toString())
                        preferences.setValues("status","1")

                        var intent = Intent(this@SignInActivity,HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity,"Password anda salah",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity,databaseError.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}
