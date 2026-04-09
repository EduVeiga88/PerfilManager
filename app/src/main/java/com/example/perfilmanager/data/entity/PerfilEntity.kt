package com.example.perfilmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfil")
data class PerfilEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val username: String,
    val password: String
)
