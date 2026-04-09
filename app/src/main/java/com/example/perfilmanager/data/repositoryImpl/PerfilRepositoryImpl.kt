package com.example.perfilmanager.data.repositoryImpl

import com.example.perfilmanager.data.dao.PerfilDao
import com.example.perfilmanager.data.entity.PerfilEntity
import com.example.perfilmanager.domain.model.Perfil
import com.example.perfilmanager.domain.repository.PerfilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PerfilRepositoryImpl(
    private val dao: PerfilDao
): PerfilRepository {
    override suspend fun insertPerfil(perfil: Perfil) {
        val entity = PerfilEntity(
            id = perfil.id,
            name = perfil.name,
            username = perfil.username,
            password = perfil.password
        )
        dao.insertPerfil(entity)
    }

    override suspend fun deletePerfil(id: Long) {
        val existingPerfil = dao.getById(id) ?: return
        dao.deletePerfil(existingPerfil)
    }

    override suspend fun updatePerfil(perfil: Perfil) {
        val entity = PerfilEntity(
            id = perfil.id,
            name = perfil.name,
            username = perfil.username,
            password = perfil.password
        )
        dao.updatePerfil(entity)
    }

    override fun getAll(): Flow<List<Perfil>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                Perfil(
                    id = entity.id,
                    name = entity.name,
                    username = entity.username,
                    password = entity.password
                )
            }
        }
    }

    override suspend fun getById(id: Long): Perfil? {
        return dao.getById(id)?.let { entity ->
            Perfil(
                id = entity.id,
                name = entity.name,
                username = entity.username,
                password = entity.password
            )
        }
    }
}