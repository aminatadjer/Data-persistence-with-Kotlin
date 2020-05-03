package com.example.exercice3.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity

data class Intervention (
    val date:String,
    val num:String,
    val plombier:String,
    val type:String

):Serializable{
    @PrimaryKey(autoGenerate = true)
    var idInterv:Int=0
}