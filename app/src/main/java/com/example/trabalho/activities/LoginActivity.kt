package com.example.trabalho.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.databinding.ActivityLoginBinding
import com.example.trabalho.models.Users

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDefault : Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDefault = Users(1, "admin", "12345")

        binding.btLogin.setOnClickListener {
            val username = binding.etUser.text.toString()
            val password = binding.etPassword.text.toString()

            if(username == userDefault.user && password == userDefault.password) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(this, "Username or Password incorret. Please, try again!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}