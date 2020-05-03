package com.example.exercice3.db

import com.example.exercice3.db.dao.InterventionDao
import com.example.exercice3.db.dao.PlombieDao
import com.example.exercice3.db.entities.Intervention
import com.example.exercice3.db.entities.Plombier

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.util.concurrent.locks.Lock

@Database(
    entities = [Intervention::class,Plombier::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class InterventionDataBase : RoomDatabase() {
    abstract fun getPlombierDao(): PlombieDao
    abstract fun getInterventionDao(): InterventionDao



    companion object {
        @Volatile
        private var instance: InterventionDataBase? = null
        private val Lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?:buildDataBase(context).also {
                instance= it
            }

        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            InterventionDataBase::class.java,
            "database"
        ).build()
    }
}