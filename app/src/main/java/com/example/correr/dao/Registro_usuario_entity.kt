package com.example.correr.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registro")
data class Registro_usuario_entity (
    @PrimaryKey(autoGenerate = true)
    val id:          Int?= null,
    @ColumnInfo(name = "nombre")
    val nombre:      String,
    @ColumnInfo(name = "contraseña")
    val contraseña : String,
    @ColumnInfo(name = "correo")
    val correo :     String,




)


