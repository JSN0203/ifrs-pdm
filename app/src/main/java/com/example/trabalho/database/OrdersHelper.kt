package com.example.trabalho.database

import android.content.ContentValues
import com.example.trabalho.models.Orders

class OrdersHelper(private val dbHelper: DatabaseHelper) {
    companion object {
        private const val TABLE_ORDERS = "orders"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TOTAL_PRICE = "total_price"
        private const val COLUMN_STATUS = "status"
        private const val COLUMN_OBS = "obs"
    }

    val CREATE_TABLE = "CREATE TABLE $TABLE_ORDERS (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_DATE TEXT, " +
            "$COLUMN_TOTAL_PRICE REAL, " +
            "$COLUMN_STATUS TEXT, " +
            "$COLUMN_OBS TEXT)"

    fun addOrder(order: Orders) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, order.orderDate)
            put(COLUMN_TOTAL_PRICE, order.totalPrice)
            put(COLUMN_STATUS, order.status)
            put(COLUMN_OBS, order.obs)
        }
        db.insert(TABLE_ORDERS, null, values)
        db.close()
    }

    fun getOrders() : List<Orders> {
        val orders = mutableListOf<Orders>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_ORDERS, arrayOf(COLUMN_ID, COLUMN_DATE, COLUMN_STATUS, COLUMN_TOTAL_PRICE, COLUMN_OBS), null, null, null, null, null)
        with(cursor){
            while (moveToNext()) {
                val order = Orders(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    orderDate = getString(getColumnIndexOrThrow(COLUMN_DATE)),
                    totalPrice = getDouble(getColumnIndexOrThrow(COLUMN_TOTAL_PRICE)),
                    status = getString(getColumnIndexOrThrow(COLUMN_STATUS)),
                    obs = getString(getColumnIndexOrThrow(COLUMN_OBS))
                )
            }
            close()
        }
        db.close()
        return orders
    }

    fun getOrdersById(id: Int): Orders? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_ORDERS, arrayOf(COLUMN_ID, COLUMN_DATE, COLUMN_STATUS, COLUMN_TOTAL_PRICE, COLUMN_OBS), "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)

        var order : Orders? = null
        with(cursor){
            if (moveToFirst()) {
                order = Orders(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    orderDate = getString(getColumnIndexOrThrow(COLUMN_DATE)),
                    totalPrice = getDouble(getColumnIndexOrThrow(COLUMN_TOTAL_PRICE)),
                    status = getString(getColumnIndexOrThrow(COLUMN_STATUS)),
                    obs = getString(getColumnIndexOrThrow(COLUMN_OBS))
                )
            }
            close()
        }
        db.close()
        return order
    }

    fun updateOrder(order: Orders): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, order.orderDate)
            put(COLUMN_TOTAL_PRICE, order.totalPrice)
            put(COLUMN_STATUS, order.status)
            put(COLUMN_OBS, order.obs)
        }
        return db.update(TABLE_ORDERS, values, "$COLUMN_ID = ?", arrayOf(order.id.toString()))
    }

    fun deleteOrder(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(TABLE_ORDERS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}