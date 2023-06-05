package com.aditd5.bwamov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aditd5.bwamov.R
import com.aditd5.bwamov.sign.signin.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpActivity : AppCompatActivity() {

    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText

    private lateinit var  mFirebaseDatabaseReference: DatabaseReference
    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignup = findViewById(R.id.btnSignup)
        etUsername = findViewById(R.id.etUsernameSU)
        etPassword = findViewById(R.id.etPasswordSU)
        etName = findViewById(R.id.etNameSU)
        etEmail = findViewById(R.id.etEmailSU)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabaseReference = mFirebaseInstance.getReference("User")

        btnSignup.setOnClickListener {
            val iUsername = etUsername.text.toString()
            val iPassword = etPassword.text.toString()
            val iName = etName.text.toString()
            val iEmail = etEmail.text.toString()

            if (iUsername.equals("")) {
                etUsername.error = "Silahkan masukkan username anda"
                etUsername.requestFocus()
            } else if (iPassword.equals("")) {
                etPassword.error = "Silahkan masukkan password anda"
                etPassword.requestFocus()
            } else if (iName.equals("")) {
                etName.error = "Silahkan masukkan nama anda"
                etName.requestFocus()
            } else if (iEmail.equals("")) {
                etEmail.error = "Silahkan masukkan email anda"
                etEmail.requestFocus()
            } else {
                saveUser (iUsername, iPassword, iName, iEmail)
            }
        }
    }

    private fun saveUser(iUsername: String, iPassword: String, iName: String, iEmail: String) {
        var user = User()
        user.username = iUsername
        user.password = iPassword
        user.nama = iName
        user.email = iEmail

        if (iUsername != null) {
            checkingUsername(iUsername,user)
        }
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabaseReference.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseDatabaseReference.child(iUsername).setValue(data)

                    var intent = Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(intent)
                } else if (user != null) {
                    Toast.makeText(this@SignUpActivity,"Username sudah digunakan",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity,""+databaseError.message,Toast.LENGTH_SHORT).show()
            }
        })
    }
}