package com.example.correr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button_iniciar_sesion = findViewById<Button>(R.id.button5)
        button_iniciar_sesion.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val botton_registrarse = findViewById<Button>(R.id.button4)
        botton_registrarse.setOnClickListener {
            val intent = Intent(this, distancia::class.java)
            startActivity(intent)
        }
        val botton_salir = findViewById<Button>(R.id.button3)
        botton_salir.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}