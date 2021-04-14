package com.kotlin.imc_app

import android.app.ActionBar
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var visibleAltura = 0
    var visiblePeso = 0
    var stringTemp = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()

    }

    private fun setListeners(){
        alturaEt.doOnTextChanged{ text, start, before, count ->
            if (count > 0) {
                visibleAltura = 1
                visible()
            }
            if (text.toString().isEmpty()) {
                visibleAltura = 0
                visible()
            }
            //titleTv.text = text
            //Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()
        }
        pesoEt.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                visiblePeso = 1
                visible()
            }
            if (text.toString().isEmpty()) {
                visiblePeso = 0
                visible()
            }
        }
        calcularBt.setOnClickListener {
            calcularIMC(pesoEt.text.toString(), alturaEt.text.toString())
        }

        voltarBt.setOnClickListener {
            voltar()
        }
    }

    private fun visible(): Unit {
        if(visibleAltura + visiblePeso == 2)
            group.isVisible = true
        else
            group.isGone = true
    }

    private fun calcularIMC(peso: String, altura: String){
        val peso = peso.toFloatOrNull()
        val altura = altura.toFloatOrNull()

        if (peso != null && altura != null){
            val imc = peso / (altura * altura)
            titleTv.text = "Seu IMC é: \n%.2f".format(imc)
        }
        alturaEt.isGone = true
        pesoEt.isGone = true
        calcularBt.isGone = true
        groupReturn.isVisible = true
    }

    private fun voltar(){
        groupReturn.isGone = true
        alturaEt.isVisible = true
        pesoEt.isVisible = true
        calcularBt.isVisible = true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT)
        // Checks the orientation of the screen

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            params.setMargins( 0, 0, 0, 0)
            titleTv.layoutParams = params

            //Toast.makeText(this, titleTv.text.toString().replace("\n",""), Toast.LENGTH_SHORT).show()
            stringTemp = titleTv.text.toString()
            titleTv.text = titleTv.text.toString().replace("\n","")
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            params.setMargins( 0, 48, 0, 0)
            titleTv.layoutParams = params
            titleTv.text = stringTemp
        }
    }
}