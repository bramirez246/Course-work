package com.example.pomodoroclock

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pomodoroclock.databinding.ActivityCreateBinding
import com.google.firebase.auth.FirebaseAuth

class CreateActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityCreateBinding
    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        user = FirebaseAuth.getInstance()
        viewBinding.createBtn.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser()
    {
        var email = viewBinding.CreateEmail.text.toString()
        var password = viewBinding.CreatePassword.text.toString()
        var repass = viewBinding.repeatPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty() && repass.isNotEmpty())
        {
            if(password == repass){
                user.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(CreateActivity()){ task->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "User Added Successfully.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Passwords don't match.", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Email and Password cannot be empty.", Toast.LENGTH_SHORT).show()
        }
    }
}