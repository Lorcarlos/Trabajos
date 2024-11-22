package com.example.correr

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class principal : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var stepDetectorSensor: Sensor? = null

    private var steps = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)

        // Inicializa los sensores y el sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        // Configuración de los elementos de la interfaz

        val button_salir = findViewById<Button>(R.id.button3)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)



        button_salir.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Inicializa la visualización de los pasos
        textViewResultado.text = "Kilómetros: 0.0"

        // Registrar el listener para el sensor de pasos
        if (stepCounterSensor == null) {
            Toast.makeText(this, "Sensor de pasos no disponible en tu dispositivo.", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    // Lógica para contar pasos
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                // Obtiene el número de pasos desde el sensor
                steps = event.values[0].toInt()
                val km = pasosAKm(steps)
                findViewById<TextView>(R.id.textViewResultado).text = "Kilómetros: %.2f".format(km)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario para este caso, pero lo implementamos como parte del listener
    }

    // Función para convertir pasos a kilómetros
    private fun pasosAKm(pasos: Int): Double {
        val pasosPorKm = 1500
        return pasos / pasosPorKm.toDouble()
    }

    override fun onResume() {
        super.onResume()
        // Registra el sensor cuando la actividad está en primer plano
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        // Desregistra el sensor cuando la actividad está en segundo plano
        sensorManager.unregisterListener(this)
    }
}
