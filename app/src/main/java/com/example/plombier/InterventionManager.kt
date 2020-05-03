package com.example.plombier

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import java.util.function.Predicate

class InterventionManager{
    var fileManager = FileManager()
    fun getInterventions(dir:String):MutableList<Intervention>{
        var interventions:MutableList<Intervention>
        val file = dir + "/intervention"
        var list= fileManager.read(file)
        if (list.isNotEmpty()){
            val mutableList = object : TypeToken<MutableList<Intervention>>() {}.type
            var gson = Gson()
            interventions = gson.fromJson(list,mutableList)
        }else{interventions = mutableListOf()}

        return interventions
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteInterventions(dir: String, index:Int): MutableList<Intervention> {
        var interventions = getInterventions(dir)
        interventions.removeIf { i-> i.num == index}
        updateData(dir,interventions)
        return interventions
    }

    fun editIntervention(dir: String,i:Int,intervention: Intervention): MutableList<Intervention> {
        val interventions = getInterventions(dir)
        interventions[i] = intervention
        updateData(dir,interventions)
        return interventions
    }
    fun addIntervention(dir: String,intervention: Intervention): MutableList<Intervention> {
        val interventions = getInterventions(dir)
        interventions.add(intervention)
        updateData(dir,interventions)
        return interventions
    }

    fun updateData(dir:String,interventions: MutableList<Intervention>){
        var gson = Gson()
        var json:String = gson.toJson(interventions)
        val file = dir + "/intervention"
        fileManager.write(json,file)

    }

    fun searchPerDate(dir: String,calendar: String):MutableList<Intervention>{
        var interventions = getInterventions(dir)
        var result = interventions.filter { intervention -> intervention.date.equals(calendar)  }
        return result.toMutableList()
    }
}


