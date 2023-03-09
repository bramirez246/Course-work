package com.example.pomodoroclock

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pomodoroclock.databinding.ActivityCreateBinding
import com.example.pomodoroclock.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity: AppCompatActivity() {
    private lateinit var viewBinding:ActivityLoginBinding
    private lateinit var user:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        user = FirebaseAuth.getInstance()

        viewBinding.signIn.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser()
    {
        var email = viewBinding.LogEmail.text.toString()
        var password = viewBinding.LogPassword.text.toString()


        if(email.isNotEmpty() && password.isNotEmpty())
        {
            user.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateActivity()){ task->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "User Signed in Successfully.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HolderActivity::class.java))
                    }
                    else{
                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else{
            Toast.makeText(this, "Email and Password are incorrect.", Toast.LENGTH_SHORT).show()
        }
    }
}