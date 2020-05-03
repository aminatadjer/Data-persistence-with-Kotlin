package com.example.exercice3.db.dao

import androidx.room.*
import com.example.exercice3.db.entities.Plombier

@Dao
interface PlombieDao {
    @Insert
    suspend fun addPlumber(plombier: Plombier)

    @Query("Select * From Plombier Order BY id DESC")
    suspend fun getAllPlumbers(): List<Plombier>

    @Update
    suspend fun updatePlumber(plombier: Plombier)

    @Delete
    suspend fun deletePlumber(plombier: Plombier)
}