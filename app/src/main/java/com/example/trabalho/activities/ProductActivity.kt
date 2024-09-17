package com.example.trabalho.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.database.ProductsHelper
import com.example.trabalho.databinding.ActivityProductBinding
import com.example.trabalho.models.Products

class ProductActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productsHelper: ProductsHelper
    private var productList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsHelper = ProductsHelper(DatabaseHelper(this))

        loadProducts()

        binding.btnAdd.setOnClickListener {
            addProduct()
        }

        binding.btnGoBack.setOnClickListener {
            finish()
        }

        binding.lvProducts.setOnItemClickListener { adapterView, view, i, l ->
            val selectProduct = productsHelper.getProducts()[i]
            val intent = Intent(this, EditProductActivity::class.java)
            intent.putExtra("PRODUCT_ID", selectProduct.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadProducts()
    }

    private fun addProduct() {
        val name = binding.etName.text.toString()
        val description = binding.etDescription.text.toString()
        val price = binding.etPrice.text.toString()
        val quantity = binding.etQuantity.text.toString()

        if(name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty()) {
            val product = Products(name = name, description = description, price = price.toDouble(), quantity = quantity.toInt())
            productsHelper.addProduct(product)
            Toast.makeText(this, "Product was added!", Toast.LENGTH_SHORT).show()

            binding.etName.text.clear()
            binding.etDescription.text.clear()
            binding.etPrice.text.clear()
            binding.etQuantity.text.clear()

            loadProducts()
        } else {
            Toast.makeText(this, "Please, fill all the inputs", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadProducts() {
        productList.clear()

        val products = productsHelper.getProducts()
        for (product in products) {
            productList.add("ID: ${product.id}, Name: ${product.name}, Description: ${product.description}, Price: ${product.price}, Quantity: ${product.quantity} ")
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productList)
        binding.lvProducts.adapter = adapter
    }
}