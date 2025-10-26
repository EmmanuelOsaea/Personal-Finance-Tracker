package com.example.financetracker.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {
    fun loadCategories(context: Context): List<Category> {
        return try {
            val json = context.assets.open("categories.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<Category>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        }
    }
}
