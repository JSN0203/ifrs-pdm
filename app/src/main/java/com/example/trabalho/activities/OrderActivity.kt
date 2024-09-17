package com.example.trabalho.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.database.OrdersHelper
import com.example.trabalho.databinding.ActivityOrderBinding
import com.example.trabalho.models.Orders

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderHelper : OrdersHelper
    private  var orderList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderHelper = OrdersHelper(DatabaseHelper(this))

        loadOrders()

        binding.btnAdd.setOnClickListener {
            addOrders()
        }

        binding.btnGoBack.setOnClickListener {
            finish()
        }

        binding.lvOrders.setOnItemClickListener { adapterView, view, i, l ->
            val selectedOrder = orderHelper.getOrders()[i]
            val intent = Intent(this, EditOrderActivity::class.java)
            intent.putExtra("ORDER_ID", selectedOrder.id)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        loadOrders()
    }

    private fun addOrders() {
        val date = binding.etDate.text.toString()
        val price = binding.etPrice.text.toString()
        val status = binding.etStatus.text.toString()
        val obs = binding.etObs.text.toString()

        if(date.isNotEmpty() && price.isNotEmpty() && status.isNotEmpty() && obs.isNotEmpty()) {
            val order = Orders(orderDate = date, totalPrice = price.toDouble(), status = status, obs = obs)
            orderHelper.addOrder(order)

            Toast.makeText(this, "Order was added!", Toast.LENGTH_SHORT).show()

            binding.etDate.text.clear()
            binding.etPrice.text.clear()
            binding.etStatus.text.clear()
            binding.etObs.text.clear()

            loadOrders()
        } else {
            Toast.makeText(this, "Please, fill all the inputs!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadOrders() {
        orderList.clear()
        var orders = orderHelper.getOrders()
        for (order in orders) {
            orderList.add("ID: ${order.id}, Date: ${order.orderDate}, Total Price:${order.totalPrice}, Status: ${order.status}, Observations: ${order.obs}")
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orderList)
        binding.lvOrders.adapter = adapter
    }

}