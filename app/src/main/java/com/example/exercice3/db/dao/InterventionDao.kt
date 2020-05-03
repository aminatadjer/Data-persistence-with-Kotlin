package com.example.exercice3.db.dao

import androidx.room.*
import com.example.exercice3.db.entities.Intervention
import com.example.exercice3.db.entities.Plombier

@Dao
interface InterventionDao {
    @Insert
    suspend fun addIntervention(intervention: Intervention)

    @Query("Select * From Intervention Order BY idInterv DESC")
    suspend fun getAllInterventions(): List<Intervention>

    @Update
    suspend fun updateIntervention(intervention: Intervention)

    @Delete
    suspend fun deleteIntervention(intervention: Intervention)

    @Query("Select * From Intervention WHERE date LIKE:plm" )
    suspend fun getInterventionByDate(plm:String): List<Intervention>
}