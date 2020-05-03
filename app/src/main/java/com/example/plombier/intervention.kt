package com.example.plombier;

import java.util.*

class Intervention{
    companion object{
        var id:Int = 0
    }
    var num:Int = Intervention.id
    var date = ""
    var plombier:String =""
    var type:String = ""
    init {
        Intervention.id++
    }
}