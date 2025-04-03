package com.example.ora

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SpentEntity::class, CategoryEntity::class], version = 1)
abstract class OrcaiDatabase: RoomDatabase(){

    abstract fun getSpentDao(): SpentDao

    abstract fun getCategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: OrcaiDatabase? = null

        fun getDatabase(context: Context): OrcaiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrcaiDatabase::class.java,
                    "orcai_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
