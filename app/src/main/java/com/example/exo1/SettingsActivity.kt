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
        val sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        val editor = sp.edit()
        setContentView(R.layout.activity_settings)
        mainLayout.setBackgroundColor(sp.getInt("your_int_key", -1))
        changeColorSettings.setOnClickListener() {
            val colorPicker = ColorPicker(this)
            val colors: ArrayList<String> = ArrayList()
            colors.add("#82B926")
            colors.add("#a276eb")
            colors.add("#6a3ab2")
            colors.add("#666666")
            colors.add("#FFFF00")
            colors.add("#3C8D2F")
            colors.add("#FA9F00")
            colors.add("#FF0000")
            colorPicker
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(object : OnChooseColorListener {
                    override fun onChooseColor(position: Int, color: Int) {
                        editor.putInt("your_int_key",color)
                        editor.commit()
                        val intent = intent // from getIntent()
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        finish()
                        startActivity(intent)
                    }

                    override fun onCancel() {}
                })
                .show()
        }
        retour.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}
