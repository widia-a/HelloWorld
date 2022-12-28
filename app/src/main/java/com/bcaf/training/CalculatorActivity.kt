package com.bcaf.training

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculator.*
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.*
import org.mariuszgromada.math.mxparser.mXparser


class CalculatorActivity : AppCompatActivity() {

    var lastComma: Boolean = false
    var lastDigit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        btnLeave.setOnClickListener(){
            finish()
        }
    }

    fun onBtnPress(view: View){
        inputTxt.append((view as Button).text)
        lastComma = false
        lastDigit = true
    }

    fun onClear(view: View){
        inputTxt.setText("")
    }

    fun onOperatorPress(view:View){
        if(lastDigit && !lastComma){
            inputTxt.append((view as Button).text)
            lastDigit = false
            lastComma = false
        }
    }

    fun onCommaPress(view: View){
        if (lastDigit && !lastComma){
            inputTxt.append(".")
            lastDigit = false
            lastComma = true
        }
    }

    fun onEqualPress(view: View){
        var count = Expression(inputTxt.text.toString())
        inputTxt.setText(count.calculate().toString())
    }

}