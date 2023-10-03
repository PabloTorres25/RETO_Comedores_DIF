package com.itesm.aplicacioncomedor.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itesm.aplicacioncomedor.R
import android.os.Handler


@SuppressLint("CustomSplashScreen") // Ignora una advertencia por la actividad que sale antes del
                                        // main Activity (Logo del DIF)
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_de_introduccion)

        // Espera medio segundo y luego navega a la pantalla principal
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 500) // 500 milisegundos (medio segundo)
    }
}