package com.example.exercice4.db
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*

@Entity
data class Note (
    val title: String,
    val note: String,
    val date:String

):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}


