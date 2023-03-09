package com.example.pomodoroclock


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var login = findViewById<Button>(R.id.signIn)
        var createAct = findViewById<Button>(R.id.CreateAct)

        login.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        createAct.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }
}