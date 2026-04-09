package com.example.perfilmanager.domain.repository

import com.example.perfilmanager.domain.model.Perfil
import kotlinx.coroutines.flow.Flow

interface PerfilRepository {

    suspend fun insertPerfil(perfil: Perfil)

    suspend fun deletePerfil(id: Long)

    suspend fun updatePerfil(perfil: Perfil)

    fun getAll(): Flow<List<Perfil>>

    suspend fun getById(id: Long): Perfil?
}