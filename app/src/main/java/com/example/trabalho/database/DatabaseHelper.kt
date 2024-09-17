package com.example.trabalho.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "assignment.db"
        private const val DATABASE_VERSION = 2
    }

    private val ch = ClientHelper(this)
    private val oh = OrdersHelper(this)
    private val ph = ProductsHelper(this)

    override fun onCreate(db: SQLiteDatabase) {
        ch.createTable(db)
        oh.createTable(db)
        ph.createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        ch.dropTable(db)
        oh.dropTable(db)
        ph.dropTable(db)

        onCreate(db)
    }
}