package com.example.financetracker.db

import androidx.room.*
import com.example.financetracker.data.Transaction

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAllTransactions(): List<Transaction>

    @Insert
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}
