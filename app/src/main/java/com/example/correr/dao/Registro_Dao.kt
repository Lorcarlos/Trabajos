package com.example.correr.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Registro_Dao {


    @Query("SELECT * FROM registro")
    fun getAllRegistros(): Flow<List<Registro_usuario_entity>>


    @Query("SELECT * FROM registro WHERE correo = :correo LIMIT 1")
    fun getRegistroByCorreo(correo: String): Flow<Registro_usuario_entity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistro(registro: Registro_usuario_entity)
}









