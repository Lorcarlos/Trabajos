package com.example.correr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.inputmethod.InputBinding


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button_iniciar_sesion = findViewById<Button>(R.id.button1)
        button_iniciar_sesion.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
        val botton_registrarse = findViewById<Button>(R.id.button2)
        botton_registrarse.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }


    }





}