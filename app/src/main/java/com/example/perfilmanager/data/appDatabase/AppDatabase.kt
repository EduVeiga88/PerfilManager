package com.example.perfilmanager.data.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perfilmanager.data.dao.PerfilDao
import com.example.perfilmanager.data.entity.PerfilEntity


@Database(
    entities = [PerfilEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun perfilDao() : PerfilDao
}

object AppDatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null


    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "perfilmanager_database"
            ).build()


            INSTANCE = instance
            instance
        }
    }
}
