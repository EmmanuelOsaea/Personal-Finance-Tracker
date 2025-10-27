package com.example.financetracker.data.local 

import androidx.room.*
import com.example.financetracker.data.Transaction
import kotlinx.coroutines.flow.Flow
import com.example.financetracker.model.Transaction
import androidx.lifecycle.LiveData

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


@Query("SELECT * FROM transactions WHERE title LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' ORDER BY date DESC")
fun searchTransactions(query: String): LiveData<List<TransactionEntity>>

@Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
fun filterByDate(startDate: String, endDate: String): LiveData<List<TransactionEntity>>
