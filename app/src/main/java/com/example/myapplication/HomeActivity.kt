package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val actionBar = supportActionBar
        actionBar?.hide()

        val button = findViewById<Button>(R.id.btnMain)
        val button_favoris = findViewById<Button>(R.id.btnFavorite)

        button.setOnClickListener{

            startActivity(Intent(this, MainActivity::class.java).apply {

            })


            button_favoris.setOnClickListener{
                Log.d("gkzfj","nope")
                startActivity(Intent(this,YoutubePlayer::class.java).apply {  })
            }



        }
    }






}