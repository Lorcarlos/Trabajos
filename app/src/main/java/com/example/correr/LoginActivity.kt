package com.example.correr

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.correr.BD.AppDatabase
import com.example.correr.ViewModels.ViewModelusuario
import com.example.correr.ViewModels.ViewModelusuarioFactory
import com.example.correr.dao.Registro_Dao

import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var correoEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var viewModel: ViewModelusuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializamos los EditTexts
        correoEditText = findViewById(R.id.emailEditText)
        contrasenaEditText = findViewById(R.id.passwordEditText)

        val loginButton = findViewById<Button>(R.id.loginButton1)
        loginButton.setOnClickListener {
            val correo = correoEditText.text.toString().trim()
            val contrasena = contrasenaEditText.text.toString().trim()

            // Validar los campos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar formato de correo
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Por favor ingresa un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ocultar el teclado cuando el usuario haga clic en el botón de login
            hideKeyboard()

            // Verificar el login en la base de datos
            verificarLogin(correo, contrasena)
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }

        // Obtén la instancia de la base de datos Room
        val dao: Registro_Dao = AppDatabase.getDatabase(applicationContext).registroDao()

        // Crea el ViewModel usando el factory
        val factory = ViewModelusuarioFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(ViewModelusuario::class.java)
    }

    private fun verificarLogin(correo: String, contrasena: String) {
        lifecycleScope.launch {
            viewModel.getRegistroByCorreo(correo).collect { registro ->
                if (registro != null) {
                    if (registro.contraseña == contrasena) {
                        val intent = Intent(this@LoginActivity, principal::class.java)
                        startActivity(intent)
                        finish() // Finaliza la actividad de login
                    } else {
                        Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }
}