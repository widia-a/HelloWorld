package com.bcaf.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var container: LinearLayout

    val defaultPassword = "12345"
    val defaultUsername = "admin"
    var counter = 0

    fun init(){
        container = findViewById(R.id.containerDummy)

        btnLogin.setOnClickListener(View.OnClickListener {
            checkPassword(it)
//            var button = Button(applicationContext)
//            button.text = "Button" + counter++
//            containerDummy.addView(button)

        })
        textForgot.setOnClickListener {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun checkPassword(v : View){
        if (txtUsername.text.contentEquals(defaultUsername) && txtPassword.text.contentEquals(defaultPassword)){
            Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_LONG).show()
//            val intent = Intent(this, MenuActivity::class.java)
            val intent = Intent(this, PortoActivity::class.java)
//            intent.putExtra("username", txtUsername.text.toString())
//            intent.putExtra("password", txtPassword.text.toString())
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Username/Password tidak valid", Toast.LENGTH_LONG).show()
        }
    }

    fun forgotPassword(v: View){
        Toast.makeText(applicationContext, "Pulihkan akun", Toast.LENGTH_LONG).show()

    }


}