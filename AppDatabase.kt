package com.example.financetracker.data

import androidx.room.Room
import androidx.room.Database
import com.example.financetracker.data.Transaction
import android.content.Context
import androidx.room.RoomDatabase

@Database(entities = [Transaction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}


companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "finance_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
