package com.example.trabalho.database

import android.content.Context

class TestHelper(dbHelper: DatabaseHelper) {
    companion object {
        private const val TABLE_CLIENTS = "clients"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_BIRTH_DATE = "birth_date"
    }

    val CREATE_TABLE = "CREATE TABLE $TABLE_CLIENTS (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_NAME TEXT, " +
            "$COLUMN_ADDRESS TEXT," +
            "$COLUMN_PHONE TEXT, " +
            "$COLUMN_EMAIL TEXT, " +
            "$COLUMN_BIRTH_DATE TEXT)"


}