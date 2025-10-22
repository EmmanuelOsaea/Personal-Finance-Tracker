package com.example.financetracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financetracker.data.Transaction

@Database(entities = [Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
