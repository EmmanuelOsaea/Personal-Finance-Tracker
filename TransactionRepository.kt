package com.example.financetracker.data

import android.content.Context
import androidx.room.Room
import com.example.financetracker.db.AppDatabase

class TransactionRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "finance-db"
    ).build()

    suspend fun insert(transaction: Transaction) = db.transactionDao().insert(transaction)
    suspend fun delete(transaction: Transaction) = db.transactionDao().delete(transaction)
    suspend fun getAll() = db.transactionDao().getAllTransactions()
}
