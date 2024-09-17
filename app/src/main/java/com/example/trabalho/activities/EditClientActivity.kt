package com.example.trabalho.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.ClientHelper
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.databinding.ActivityEditClientBinding
import com.example.trabalho.models.Clients

class EditClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditClientBinding
    private lateinit var clientHelper: ClientHelper
    private var clientId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clientHelper = ClientHelper(DatabaseHelper(this))

        clientId = intent.getIntExtra("CLIENT_ID", 0)

        loadClientData(clientId)


        binding.btnSave.setOnClickListener {
            save()
        }

        binding.btnDelete.setOnClickListener {
            confirmedDelete()
        }

    }

    private fun loadClientData(id: Int){
        val client = clientHelper.getClientsById(id)
        if (client != null) {
            binding.etName.setText(client.name)
            binding.etEmail.setText(client.email)
            binding.etPhone.setText(client.phone)
            binding.etAddress.setText(client.address)
            binding.etBirthDate.setText(client.birthDate)
        }
    }

    private fun save() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val address = binding.etAddress.text.toString()
        val birthDate = binding.etBirthDate.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && birthDate.isNotEmpty()) {
            val client = Clients(id = clientId, name = name,email = email, phone = phone, address = address, birthDate = birthDate)
            clientHelper.updateClient(client)
            finish()
        } else {
            Toast.makeText(this, "Please, fill all the inputs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmedDelete() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Client")
        builder.setMessage("Are you sure you want to delete this client?")
        builder.setPositiveButton("Yes") {dialog, which ->
            deleteClient()
        }
        builder.setNegativeButton("No") {dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteClient() {
        val result = clientHelper.deleteClient(clientId)
        if (result > 0) {
            Toast.makeText(this, "Client was deleted with success!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "An error happened", Toast.LENGTH_SHORT).show()
        }
    }
}