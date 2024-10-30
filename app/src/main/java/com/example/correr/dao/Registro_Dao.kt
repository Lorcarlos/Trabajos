package com.example.correr.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Registro_Dao {

    @Query("SELECT * FROM registro")
     fun getAllRegistros(): List<Registro_usuario_entity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRegistro(registro: Registro_usuario_entity)
}








