package com.bcaf.training

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_menu.*
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.Period.between
import java.util.*

class MenuActivity : AppCompatActivity() {
     var username = ""
     var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val datas : Bundle? = intent.extras
        username = datas?.getString("username", "").toString()
        password = datas?.getString("password", "").toString()

        textHello.text = "Selamat datang $username"

        animateText()

        btnPickDate.setOnClickListener(
            View.OnClickListener { pickDate() }
        )

        btnDial.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:087879990034")
            }
            startActivity(intent)
        })
    }

    fun animateText(){
        val anim = AlphaAnimation(0.0f, 1f)
        anim.duration = 150
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        textHello.startAnimation(anim)
    }

    fun pickDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int
            )
            {
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val usia = Period.between(LocalDate.of(year, monthOfYear, dayOfMonth), LocalDate.now())
//                txtUsia.setText("${usia.years} tahun ${ if(usia.months >= 12) usia.months % 12 else usia.months} bulan")
                txtUsia.setText("Usia anda ${usia.years} tahun ${usia.months-1} bulan ${usia.days} hari")

                val myFormat = "dd/MMM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                textTglLahir.text = sdf.format(c.getTime())

            }
        }

        DatePickerDialog(this,
            dateSetListener,
        c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}