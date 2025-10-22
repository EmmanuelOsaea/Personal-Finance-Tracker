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


package com.example.financetracker.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class TransactionRepository(private val context: Context) {

    private val prefs = context.getSharedPreferences("Transactions", Context.MODE_PRIVATE)

    fun getAll(): List<Transaction> {
        val list = mutableListOf<Transaction>()
        val jsonStr = prefs.getString("transactions", "[]")
        val array = JSONArray(jsonStr)
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(Transaction(
                title = obj.getString("title"),
                amount = obj.getDouble("amount"),
                category = obj.getString("category"),
                date = obj.getLong("date")
            ))
        }
        return list
    }

    fun insert(transaction: Transaction) {
        val list = getAll().toMutableList()
        list.add(transaction)
        saveList(list)
    }

    fun delete(transaction: Transaction) {
        val list = getAll().toMutableList()
        list.remove(transaction)
        saveList(list)
    }

    private fun saveList(list: List<Transaction>) {
        val array = JSONArray()
        list.forEach {
            val obj = JSONObject()
            obj.put("title", it.title)
            obj.put("amount", it.amount)
            obj.put("category", it.category)
            obj.put("date", it.date)
            array.put(obj)
        }
        prefs.edit().putString("transactions", array.toString()).apply()
    }
}
