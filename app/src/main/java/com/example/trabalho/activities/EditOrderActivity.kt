package com.example.trabalho.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho.database.DatabaseHelper
import com.example.trabalho.database.OrdersHelper
import com.example.trabalho.databinding.ActivityEditOrderBinding
import com.example.trabalho.models.Orders

class EditOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditOrderBinding
    private lateinit var orderHelper: OrdersHelper
    private var orderId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderHelper = OrdersHelper(DatabaseHelper(this))

        orderId = intent.getIntExtra("ORDER_ID", 0)

        loadOrderData(orderId)

        binding.btnSave.setOnClickListener {
            save()
        }

        binding.btnDelete.setOnClickListener {
            confirmedDelete()
        }

    }

    private fun loadOrderData(id: Int) {
        val order = orderHelper.getOrdersById(id)
        if(order != null) {
            binding.etDate.setText(order.orderDate)
            binding.etPrice.setText(order.totalPrice.toString())
            binding.etStatus.setText(order.status)
            binding.etObs.setText(order.obs)
        }
    }

    private fun save() {
        val date = binding.etDate.text.toString()
        val price = binding.etPrice.text.toString()
        val status = binding.etStatus.text.toString()
        val obs = binding.etObs.text.toString()

        if (date.isNotEmpty() && price.isNotEmpty() && status.isNotEmpty() && obs.isNotEmpty()) {
            val order =
                Orders(id = orderId, orderDate = date, totalPrice = price.toDouble(), status = status, obs = obs)
            orderHelper.updateOrder(order)
            finish()
        } else {
            Toast.makeText(this, "Please, fill all the inputs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmedDelete() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Client")
        builder.setMessage("Are you sure you want to delete this order?")
        builder.setPositiveButton("Yes") {dialog, which ->
            deleteOrder()
        }
        builder.setNegativeButton("No") {dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun deleteOrder() {
        val result = orderHelper.deleteOrder(orderId)
        if(result > 0){
            Toast.makeText(this, "Order was deleted!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "An error happened", Toast.LENGTH_SHORT).show()
        }

    }
}