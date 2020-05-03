package com.example.exo1

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import petrov.kristiyan.colorpicker.ColorPicker
import petrov.kristiyan.colorpicker.ColorPicker.OnChooseColorListener


class SettingsActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        val sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)
        val editor = sp.edit()
        setContentView(R.layout.activity_settings)
        window.decorView.setBackgroundColor(sp.getInt("background_color", -1))
        changeColorSettings.setOnClickListener() {
            val colorPicker = ColorPicker(this)
            colorPicker.show()
            colorPicker
                .setOnChooseColorListener(object : OnChooseColorListener {
                    override fun onChooseColor(position: Int, color: Int) {
                        editor.putInt("background_color",color)
                        editor.commit()
                        val intent = intent // from getIntent()
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        finish()
                        startActivity(intent)
                    }

                    override fun onCancel() {}
                })

        }
        retour.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}
