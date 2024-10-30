package com.example.correr.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.correr.dao.Registro_Dao

class ViewModelFactory(private val dao: Registro_Dao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelusuario::class.java)) {
            return ViewModelusuario(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
