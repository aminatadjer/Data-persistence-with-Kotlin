package com.example.plombier

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class FileManager{

    fun read(fileName:String):String{
        val file = File(fileName)
        if (file.exists()){
            var bufferedReader = file.bufferedReader()


            return bufferedReader.use { it.readText() }
        }else{
            return ""
        }
    }

    fun write(input:String,file:String,append:Boolean = false){
        val fileOutputStream = FileOutputStream(file,append)
        try {
            fileOutputStream.write(input.toByteArray())
        }catch (e: IOException)
        {
            e.printStackTrace()
        }finally {
            fileOutputStream.flush()
            fileOutputStream.close()
        }

    }


}