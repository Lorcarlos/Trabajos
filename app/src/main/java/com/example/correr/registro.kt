package com.example.correr
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.correr.ViewModels.ViewModelFactory
import com.example.correr.ViewModels.ViewModelusuario
import com.example.correr.BD.AppDatabase
import com.example.correr.dao.Registro_usuario_entity

class registro : AppCompatActivity() {
    private lateinit var viewModel: ViewModelusuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        // Configurar el padding para evitar el recorte con el sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el ViewModel usando el ViewModelFactory con el DAO
        val dao = AppDatabase.getDatabase(applicationContext).registroDao()
        val factory = ViewModelFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(ViewModelusuario::class.java)

        // Obtener referencias a los campos de entrada
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // Botón para registrar usuario
        val botonRegistrarse = findViewById<Button>(R.id.button2)
        botonRegistrarse.setOnClickListener {
            val nombre = etName.text.toString().trim()
            val correo = etEmail.text.toString().trim()
            val contraseña = etPassword.text.toString().trim()

            // Validación: Asegurarse de que los campos no estén vacíos
            if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Crear un nuevo usuario y pasarlo al ViewModel para su inserción
                val usuario = Registro_usuario_entity(nombre = nombre, contraseña = contraseña, correo = correo)
                viewModel.insertarUsuario(usuario)

                // Observar el mensaje del ViewModel para notificar al usuario
                viewModel.mensaje.observe(this) { mensaje ->
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    if (mensaje == "Usuario registrado correctamente") {
                        // Limpiar campos después del registro exitoso
                        etName.text.clear()
                        etEmail.text.clear()
                        etPassword.text.clear()
                    }
                    viewModel.limpiarMensaje() // Limpiar el mensaje después de mostrarlo
                }

                // Ocultar el teclado después de registrar
                hideKeyboard()
            }
        }

        // Botón para salir y regresar a MainActivity
        val botonSalir = findViewById<Button>(R.id.button6)
        botonSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Función para ocultar el teclado
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }
}
