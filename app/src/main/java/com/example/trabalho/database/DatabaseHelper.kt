package com.example.trabalho.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "assignment.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val ch = ClientHelper(this)
        val oh = OrdersHelper(this)
        val ph = ProductsHelper(this)

        db?.execSQL(ch.CREATE_TABLE)
        db?.execSQL(oh.CREATE_TABLE)
        db?.execSQL(ph.CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}