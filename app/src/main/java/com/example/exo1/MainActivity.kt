package com.example.exo1

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(sp.getInt("background_color", -1))
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button_settings = findViewById<Button>(R.id.settingsButton)
        button1.setOnClickListener{
            val intent = Intent(this, Activity1::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener{
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener{
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener{
            val intent = Intent(this, Activity4::class.java)
            startActivity(intent)
        }
        button_settings.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

}
