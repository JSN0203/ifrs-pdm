package com.example.trabalho.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.trabalho.models.Clients

class ClientHelper(private val dbHelper: DatabaseHelper) {
    companion object {
        private const val TABLE_CLIENTS = "clients"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_BIRTH_DATE = "birth_date"
    }

    fun createTable(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_CLIENTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_ADDRESS TEXT," +
                "$COLUMN_PHONE TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_BIRTH_DATE TEXT)")
    }

    fun dropTable(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
    }

    fun addClient(client: Clients) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, client.name)
            put(COLUMN_EMAIL, client.email)
            put(COLUMN_PHONE, client.phone)
            put(COLUMN_ADDRESS, client.address)
            put(COLUMN_BIRTH_DATE, client.birthDate)
        }
        db.insert(TABLE_CLIENTS,null ,values)
        db.close()
    }

    fun getClients(): List<Clients> {
        val clients = mutableListOf<Clients>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_CLIENTS, arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_ADDRESS, COLUMN_BIRTH_DATE), null, null, null, null, null)
        with(cursor) {
            while (moveToNext()){
                val client = Clients(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(COLUMN_PHONE)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    address = getString(getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    birthDate = getString(getColumnIndexOrThrow(COLUMN_BIRTH_DATE))
                )
                clients.add(client)
            }
            close()
        }
        db.close()
        return clients
    }

    fun getClientsById(id: Int): Clients? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_CLIENTS, arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_ADDRESS, COLUMN_BIRTH_DATE), "$COLUMN_ID = ?", arrayOf(id.toString()), null, null, null)

        var client : Clients? = null
        with(cursor){
            if (moveToFirst()){
                client = Clients(
                    id = getInt(getColumnIndexOrThrow(COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    phone = getString(getColumnIndexOrThrow(COLUMN_PHONE)),
                    email = getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    address = getString(getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    birthDate = getString(getColumnIndexOrThrow(COLUMN_BIRTH_DATE))
                )
            }
            close()
        }
        db.close()
        return client
    }

    fun updateClient(client: Clients): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, client.name)
            put(COLUMN_PHONE, client.phone)
            put(COLUMN_ADDRESS, client.address)
            put(COLUMN_BIRTH_DATE, client.birthDate)
            put(COLUMN_EMAIL, client.email)
        }

        return db.update(TABLE_CLIENTS, values, "$COLUMN_ID = ?", arrayOf(client.id.toString()))
    }

    fun deleteClient(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(TABLE_CLIENTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

}