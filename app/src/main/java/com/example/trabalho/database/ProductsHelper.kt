package com.example.trabalho.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.trabalho.models.Products

class ProductsHelper(private val dbHelper: DatabaseHelper) {
    companion object {
        private const val TABLE_PRODUCTS = "products"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_QUANTITY = "quantity"
    }

    fun createTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_PRODUCTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_PRICE REAL," +
                "$COLUMN_QUANTITY INTEGER)")
    }

    fun dropTable(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
    }

    fun addProduct(product: Products) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_DESCRIPTION, product.description)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_QUANTITY, product.quantity)
        }
        db.insert(TABLE_PRODUCTS, null, values)
        db.close()
    }

    fun getProducts(): List<Products> {
        val products = mutableListOf<Products>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_DESCRIPTION), null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val product = Products(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    quantity = getInt(getColumnIndexOrThrow(COLUMN_QUANTITY)),
                    price = getDouble(getColumnIndexOrThrow(COLUMN_PRICE))
                )
                products.add(product)
            }
            close()
        }
        db.close()
        return products
    }

    fun getProductById(id: Int) : Products? {

        var product : Products? = null

        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_PRODUCTS, arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_DESCRIPTION), "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)
        with(cursor) {
            while (moveToNext()) {
                product = Products(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    quantity = getInt(getColumnIndexOrThrow(COLUMN_QUANTITY)),
                    price = getDouble(getColumnIndexOrThrow(COLUMN_PRICE))
                )
            }
            close()
        }
        db.close()
        return product
    }

    fun updateProduct(product: Products): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_DESCRIPTION, product.description)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_QUANTITY, product.quantity)
        }
        return db.update(TABLE_PRODUCTS, values, "$COLUMN_ID = ?", arrayOf(product.id.toString()))
    }

    fun deleteProduct(id: Int): Int{
        val db = dbHelper.writableDatabase
        return db.delete(TABLE_PRODUCTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }
}