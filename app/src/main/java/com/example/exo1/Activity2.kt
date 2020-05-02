package com.example.exo1

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity2.*

class Activity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        setContentView(R.layout.activity2)
        mainLayout2.setBackgroundColor(sp.getInt("your_int_key", -1))
    }
}
