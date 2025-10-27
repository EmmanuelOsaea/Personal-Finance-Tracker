package com.example.financetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.databinding.ActivitySummaryBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.*

class SummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummaryBinding
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            val transactions = db.transactionDao().getAll()
            withContext(Dispatchers.Main) {
                displayMonthlySummary(transactions)
                showPieChart(transactions)
                showBarChart(transactions)
            }
        }
    }

    private fun displayMonthlySummary(transactions: List<TransactionEntity>) {
        val total = transactions.sumOf { it.amount }
        binding.tvMonthlyTotal.text = "Monthly Total: ₦%.2f".format(total)
    }

    private fun showPieChart(transactions: List<TransactionEntity>) {
        val grouped = transactions.groupBy { it.category }.mapValues { (_, list) ->
            list.sumOf { it.amount }
        }

        val entries = grouped.map { PieEntry(it.value.toFloat(), it.key) }
        val dataSet = PieDataSet(entries, "Expenses by Category")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }

    private fun showBarChart(transactions: List<TransactionEntity>) {
        val grouped = transactions.groupBy { it.category }.mapValues { (_, list) ->
            list.sumOf { it.amount }
        }

        val entries = grouped.entries.mapIndexed { index, entry ->
            BarEntry(index.toFloat(), entry.value.toFloat())
        }

        val dataSet = BarDataSet(entries, "Spending per Category")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val data = BarData(dataSet)
        data.barWidth = 0.9f

        binding.barChart.data = data
        binding.barChart.animateY(1000)
        binding.barChart.invalidate()
    }
}
