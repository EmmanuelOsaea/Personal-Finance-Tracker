package com.example.financetracker.data

import androidx.lifecycle.LiveData

class TransactionRepository(private val dao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = dao.getAllTransactions()
    val totalIncome: LiveData<Double?> = dao.getTotalIncome()
    val totalExpense: LiveData<Double?> = dao.getTotalExpense()

    suspend fun insert(transaction: Transaction) {
        dao.insert(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        dao.delete(transaction)
    }
}


