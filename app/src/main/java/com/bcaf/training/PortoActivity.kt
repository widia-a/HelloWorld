package com.bcaf.training

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_porto.*
import android.Manifest
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PortoActivity : AppCompatActivity() {

    companion object{
        private val REQUEST_CODE_PERMISSIONS = 999
        private val CAMERA_REQUEST_CAPTURE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_porto)

        btnCall.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:098765432132")
            }
            startActivity(intent)
        })

        btnMail.setOnClickListener(View.OnClickListener {
            composeEmail(arrayOf("widia.anggraeni@juaracoding.com"), "Test Mail")
        })

        btnMaps.setOnClickListener() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=wisma+bca+pondok+indah"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        imageView2.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ) {
                    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, REQUEST_CODE_PERMISSIONS)
                } else {
                    captureCamera()
                }
            }
        })

        projectCalculator.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        })

        projectPorto.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        })
    }

    private fun requestPermission(permissions: Array<String>, requestCodePermissions: Int) {

    }

    fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("mailto:")
            type = "*/*"
            putExtra(Intent.EXTRA_EMAIL, addresses)

            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_PERMISSIONS -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    captureCamera()
                }
                else
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun captureCamera(){
        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takeCamera, CAMERA_REQUEST_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CAPTURE && resultCode == RESULT_OK){
            val bitmapImage = data?.extras?.get("data") as Bitmap
            imageView2.setImageBitmap(bitmapImage)
            saveImage(bitmapImage)
        }
    }

    fun saveImage(bitmap: Bitmap){
        try {
            val tanggal = SimpleDateFormat("HHmmss_yyyyMMdd").format(Date())
            val extStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()
            val namaFile = extStorage + "/BCAF_" + tanggal + ".png"

            var file :File? = null
            file = File(namaFile)
            file.createNewFile()

            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val bitmapData = bos.toByteArray()

            val fos = FileOutputStream(file)
            fos.write(bitmapData)
            fos.flush()
            fos.close()
        }
        catch (e: java.lang.Exception){
            e.printStackTrace()
        }

    }



}