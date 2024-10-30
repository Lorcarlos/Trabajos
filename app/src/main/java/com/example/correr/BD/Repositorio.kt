//package com.example.correr.BD
//
//import com.example.correr.dao.Registro_Dao
//import com.example.correr.dao.Registro_usuario_entity
//
//class Repositorio (private val registroDao: Registro_Dao) {
//
//    suspend fun getRegistro(): List<Registro1> {
//        val entities = registroDao.getAllRegistros() // Usa el DAO correctamente
//        return entities.map {
//            Registro1(nombre = it.nombre, contraseña = it.contraseña, correo = it.correo) // Asegúrate de que Registro1 esté bien definido
//        }
//    }
//
//    // Función para insertar un nuevo registro
//    suspend fun insertRegistro(registro: Registro1) {
//        val entity = Registro_usuario_entity(nombre = registro.nombre, correo = registro.correo, contraseña = registro.contraseña) // Usa la entidad correctamente
//        registroDao.insertRegistro(entity) // Usa el DAO correctamente
//    }
//}