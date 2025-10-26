package com.example.financetracker.data.local 

import androidx.room.*
import com.example.financetracker.data.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactionsFlow(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY timestamp DESC")
    fun getByCategoryFlow(category: String): Flow<List<Transaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tx: Transaction): Long

    @Update
    fun update(transaction: Transaction)
  
    
    
    @Delete
   
    suspend fun delete(tx: Transaction)

    @Query("DELETE FROM transactions")
    suspend fun clearAll()
}
