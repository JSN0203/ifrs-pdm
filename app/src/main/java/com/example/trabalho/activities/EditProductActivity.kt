package com.example.trabalho.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.database.ProductsHelper
import com.example.trabalho.databinding.ActivityEditProductBinding
import com.example.trabalho.models.Products

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private lateinit var productsHelper: ProductsHelper
    private var productId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsHelper = ProductsHelper(DatabaseHelper(this))

        productId = intent.getIntExtra("PRODUCT_ID", 0)

        loadProductData(productId)

        binding.btnSave.setOnClickListener {
            save()
        }

        binding.btnDelete.setOnClickListener {
            confirmedDelete()
        }
    }

    private fun loadProductData(id: Int) {
        val product = productsHelper.getProductById(id)
        if (product != null) {
            binding.etName.setText(product.name)
            binding.etPrice.setText(product.price.toString())
            binding.etQuantity.setText(product.quantity.toString())
            binding.etDescription.setText(product.description)
        }
    }

    private fun save() {
        val name = binding.etName.text.toString()
        val price = binding.etPrice.text.toString()
        val quantity = binding.etQuantity.text.toString()
        val description = binding.etDescription.text.toString()

        if(name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty() && description.isNotEmpty())
        {
            val product = Products(id = productId, name = name, description = description, price = price.toDouble(), quantity = quantity.toInt())
            productsHelper.updateProduct(product)
            finish()
        } else {
            Toast.makeText(this, "Please, fill all the inputs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmedDelete() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Client")
        builder.setMessage("Are you sure you want to delete this product?")
        builder.setPositiveButton("Yes") {dialog, which ->
            deleteProduct()
        }
        builder.setNegativeButton("No") {dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteProduct() {
        val result = productsHelper.deleteProduct(productId)
        if (result > 0) {
            Toast.makeText(this, "Product was deleted with success!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "An error happened", Toast.LENGTH_SHORT).show()
        }
    }
}