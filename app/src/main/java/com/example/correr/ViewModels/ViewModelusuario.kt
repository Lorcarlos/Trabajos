package com.example.correr.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asLiveData
import com.example.correr.dao.Registro_Dao
import com.example.correr.dao.Registro_usuario_entity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModelusuario(private val dao: Registro_Dao) : ViewModel() {

    // LiveData para almacenar la lista de usuarios desde Flow
    val usuarios: LiveData<List<Registro_usuario_entity>> = dao.getAllRegistros().asLiveData()

    // LiveData para manejar mensajes de error o éxito
    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> get() = _mensaje

    // Función para insertar un usuario en la base de datos
    fun getRegistroByCorreo(correo: String): Flow<Registro_usuario_entity?> {
        return dao.getRegistroByCorreo(correo)
    }

    fun insertarUsuario(usuario: Registro_usuario_entity) {
        viewModelScope.launch {
            try {
                dao.insertRegistro(usuario)
                _mensaje.value = "Usuario registrado correctamente"
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