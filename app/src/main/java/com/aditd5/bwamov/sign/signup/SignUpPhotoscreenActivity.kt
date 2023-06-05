package com.aditd5.bwamov.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.aditd5.bwamov.home.HomeActivity
import com.aditd5.bwamov.R
import com.aditd5.bwamov.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID

class SignUpPhotoscreenActivity : AppCompatActivity(),PermissionListener {

    private lateinit var tvWelcome:TextView
    private lateinit var ivProfile:ImageView
    private lateinit var btnUpload:ImageView
    private lateinit var btnSavePhoto:Button
    private lateinit var btnSkipPhoto:Button

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    private lateinit var filePath: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        tvWelcome = findViewById(R.id.tv_welcome)
        ivProfile = findViewById(R.id.iv_profile)
        btnUpload = findViewById(R.id.btnUpload)
        btnSavePhoto = findViewById(R.id.btnSavePhoto)
        btnSkipPhoto = findViewById(R.id.btnSkipPhoto)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        tvWelcome.text = "Selamat Datang\n" + intent.getStringExtra("nama")

        btnUpload.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btnSavePhoto.visibility = View.VISIBLE
                btnUpload.setImageResource(R.drawable.ic_btn_upload)
                ivProfile.setImageResource(R.drawable.ic_user_pic)
            } else {
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()

                ImagePicker.with(this)
                    .galleryOnly()
                    .start()
            }
        }

        btnSavePhoto.setOnClickListener {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReference.child("images/"+UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Uploaded",Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }
                        finishAffinity()
                        var intent = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload " + progress.toInt() + "%")
                    }

            } else {

            }
        }

        btnSkipPhoto.setOnClickListener {
            finishAffinity()
            var intent = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//            takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
//            }
//        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this,"Anda tidak bisa mengupload foto",Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)

            btnSavePhoto.visibility = View.VISIBLE
            btnUpload.setImageResource(R.drawable.ic_btn_delete)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this,ImagePicker.getError(data),Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Task Cancelled",Toast.LENGTH_SHORT).show()
        }
    }
}