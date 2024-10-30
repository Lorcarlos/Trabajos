package com.example.correr.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correr.dao.Registro_Dao
import com.example.correr.dao.Registro_usuario_entity
import kotlinx.coroutines.launch

class ViewModelusuario(private val dao: Registro_Dao) : ViewModel() {

    // LiveData para almacenar la lista de usuarios
    private val _usuarios = MutableLiveData<List<Registro_usuario_entity>>(emptyList())
    val usuarios: LiveData<List<Registro_usuario_entity>> get() = _usuarios

    // LiveData para manejar mensajes de error o éxito
    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    // Función para obtener todos los registros de usuarios desde la base de datos
    fun obtenerUsuarios() {
        viewModelScope.launch {
            try {
                _usuarios.value = dao.getAllRegistros()
            } catch (e: Exception) {
                _mensaje.value = "Error al obtener usuarios: ${e.message}"
            }
        }
    }

    // Función para insertar un usuario en la base de datos
    fun insertarUsuario(usuario: Registro_usuario_entity) {
        viewModelScope.launch {
            try {
                dao.insertRegistro(usuario)
                _mensaje.value = "Usuario registrado correctamente"

                // Actualizar la lista de usuarios después de insertar
                obtenerUsuarios()
            } catch (e: Exception) {
                _mensaje.value = "Error al registrar usuario: ${e.message}"
            }
        }
    }

    // Función para limpiar el mensaje de estado después de mostrarlo
    fun limpiarMensaje() {
        _mensaje.value = ""
    }
}
