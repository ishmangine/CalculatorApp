package com.freewarestudio.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvDisplay:TextView? = null
    private var lastNumeric = false
    private var lastDot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById(R.id.tvDisplay)
        tvDisplay?.text = ""
    }
    fun onDigit(view:View){
        tvDisplay?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear(view: View) {
        tvDisplay?.text = ""
    }
    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot){
            tvDisplay?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view:View){
        tvDisplay?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvDisplay?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View) {
        if(lastNumeric){
            var tvValue = tvDisplay?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvDisplay?.text = removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvDisplay?.text = removeZeroAfterDot((one.toDouble()+two.toDouble()).toString())
                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvDisplay?.text = removeZeroAfterDot((one.toDouble()*two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    if(two.toInt() == 0){
                        tvDisplay?.text = getString(R.string.negationOfZero)
                    }
                    else
                        tvDisplay?.text = removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                }

            }catch (e:java.lang.ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if(value.contains(".0") && (value.length-2 == value.indexOf(".0")))
            value = value.substring(0,value.length-2)
        else if(value.contains(".")){
            val index = value.indexOf(".")
            if(value.length-4 > index){
                value = value.substring(0,index+4)
            }
        }
        return value
    }
    private fun isOperatorAdded(value : String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}