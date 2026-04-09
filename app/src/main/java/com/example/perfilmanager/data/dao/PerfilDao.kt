package com.example.perfilmanager.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.perfilmanager.data.entity.PerfilEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PerfilDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerfil(perfil: PerfilEntity)

    @Delete
    suspend fun deletePerfil(perfil: PerfilEntity)

    @Update
    suspend fun updatePerfil(perfil: PerfilEntity)

    @Query("SELECT * FROM perfil")
    fun getAll(): Flow<List<PerfilEntity>>

    @Query("SELECT * FROM perfil WHERE id = :id")
    suspend fun getById(id: Long): PerfilEntity?
}