package com.example.financetracker.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.financetracker.databinding.FragmentAnalyticsBinding
import com.example.financetracker.data.AppDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnalyticsFragment : Fragment() {

    private lateinit var binding: FragmentAnalyticsBinding
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        pieChart = binding.pieChart

        loadChartData()

        return binding.root
    }

    private fun loadChartData() {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = AppDatabase.getDatabase(requireContext()).transactionDao()
            val transactions = dao.getAllTransactions()

            val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
            val totalExpense = transactions.filter { it.type == "Expense" }.sumOf { it.amount }

            val entries = listOf(
                PieEntry(totalIncome.toFloat(), "Income"),
                PieEntry(totalExpense.toFloat(), "Expense")
            )

            val dataSet = PieDataSet(entries, "Finance Overview")
            dataSet.colors = listOf(Color.GREEN, Color.RED)
            dataSet.valueTextSize = 14f

            val data = PieData(dataSet)

            CoroutineScope(Dispatchers.Main).launch {
                pieChart.data = data
                pieChart.description.text = "Income vs Expense"
                pieChart.animateY(1000)
            }
        }
    }
}
