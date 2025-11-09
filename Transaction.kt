package com.example.financetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,               // e.g. "Salary" or "Groceries"
    val amount: Double,
    val type: String,                // "income" or "expense"
    val category: String,            // e.g. "Food", "Transport"
    val timestamp: Long = System.currentTimeMillis(),
    val note: String? = null         // Optional user note
)
