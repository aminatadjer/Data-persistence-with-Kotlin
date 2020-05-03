package com.example.exercice3.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity

data class Plombier(
    val Nom: String,
    val Prenom:String,
    val Experience:String
    ):Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}