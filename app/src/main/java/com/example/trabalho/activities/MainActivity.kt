package com.example.trabalho.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToOrders.setOnClickListener {
            val i = Intent(this, OrderActivity::class.java)
            startActivity(i)
        }

        binding.btnToClients.setOnClickListener {
            val i = Intent(this, ClientActivity::class.java)
            startActivity(i)
        }

        binding.btnToProducts.setOnClickListener {
            val i = Intent(this, ProductActivity::class.java)
            startActivity(i)
        }
    }

}