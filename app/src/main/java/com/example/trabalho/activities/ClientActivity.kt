package com.example.trabalho.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.ClientHelper
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.databinding.ActivityClientBinding
import com.example.trabalho.models.Clients

class ClientActivity: AppCompatActivity()  {

    private lateinit var clientHelper: ClientHelper
    private lateinit var binding: ActivityClientBinding
    private var clientList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clientHelper = ClientHelper(DatabaseHelper(this))

        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            addClient()
        }

        loadClients()

        binding.btnGoBack.setOnClickListener{
            finish()
        }

        binding.lvClients.setOnItemClickListener { parent, view, position, id ->
            val selectedClient = clientHelper.getClients()[position]
            val i = Intent(this, EditClientActivity::class.java)
            i.putExtra("CLIENT_ID", selectedClient.id)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        loadClients()
    }

    private fun addClient() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val address = binding.etAddress.text.toString()
        val birthDate = binding.etBirthDate.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && birthDate.isNotEmpty()) {
            val client = Clients(name = name,email = email, phone = phone, address = address, birthDate = birthDate)
            clientHelper.addClient(client)
            Toast.makeText(this, "Client was added!", Toast.LENGTH_SHORT).show()
            binding.etName.text.clear()
            binding.etEmail.text.clear()
            binding.etPhone.text.clear()
            binding.etAddress.text.clear()
            binding.etBirthDate.text.clear()
            loadClients()
        } else {
            Toast.makeText(this, "Please, fill all the inputs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadClients() {
        clientList.clear()
        val clients = clientHelper.getClients()
        for (client in clients) {
            clientList.add("ID: ${client.id}, Nome: ${client.name}, E-mail: ${client.email}, Phone: ${client.phone}, Address: ${client.address}, Bith Date: ${client.birthDate}")
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, clientList)
        binding.lvClients.adapter = adapter
    }

}