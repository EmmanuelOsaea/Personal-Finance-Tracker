package com.example.financetracker

import androidx.appcompat.app.AppCompatDelegate
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.ActivityMainBinding
import com.example.financetracker.ui.TransactionViewModel
import com.example.financetracker.ui.components.TransactionAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import com.example.financetracker.ui.HomeFragment
import com.example.financetracker.ui.AddTransactionDialog
import com.example.financetracker.ui.AnalyticsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = TransactionViewModel(this)

        adapter = TransactionAdapter(listOf()) { transaction ->
            viewModel.removeTransaction(transaction)
        }

        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        binding.rvTransactions.adapter = adapter

       binding.btnAnalytics.setOnClickListener {
    val intent = Intent(this, AnalyticsActivity::class.java)
    startActivity(intent)
}
        

   binding.btnSummary.setOnClickListener {
    val intent = Intent(this, SummaryActivity::class.java)
    startActivity(intent)
}     


<Button
    android:id="@+id/btnExport"
    android:text="Export CSV"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp" />

   
   binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
    if (isChecked) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
         <!-- Search Bar -->
    <SearchView
        android:id="@+id/searchView"
        android:queryHint="Search by title or category"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <!-- Filter by Date -->
    <LinearLayout
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"
        android:paddingTop="8dp">

        
        
         // Observe transactions
        viewModel.transactions.observe(this, Observer { list ->
            adapter.updateList(list)
            binding.tvSummary.text = "Total: $${String.format("%.2f", viewModel.getTotalAmount())}"
        })

        // Add dummy transaction for testing
        binding.btnAddTransaction.setOnClickListener {
            val transaction = Transaction(
                title = "New Expense",
                amount = (10..200).random().toDouble(),
                category = "Misc",
                date = System.currentTimeMillis()
            )
            viewModel.addTransaction(transaction)

       // Add a Spinner for categories in activity_main.xml above RecyclerView
<Spinner
    android:id="@+id/spinnerCategory"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="12dp" />

// In MainActivity.kt
val categories = listOf("All", "Food", "Transport", "Entertainment", "Misc")
val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
binding.spinnerCategory.adapter = spinnerAdapter

binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCategory = categories[position]
        val filteredList = if (selectedCategory == "All") {
            viewModel.transactions.value ?: listOf()
        } else {
            viewModel.transactions.value?.filter { it.category == selectedCategory } ?: listOf()
        }
        adapter.updateList(filteredList)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
        }
    }
}


private fun updatePieChart() {
    val transactions = viewModel.transactions.value ?: return
    val categoryMap = transactions.groupBy { it.category }
        .map { (category, list) -> PieEntry(list.sumOf { it.amount }.toFloat(), category) }

    val dataSet = PieDataSet(categoryMap, "Expenses by Category")
    dataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.CYAN)
    dataSet.valueTextSize = 12f

    val data = PieData(dataSet)
    binding.pieChart.data = data
    binding.pieChart.invalidate()
}

// Call updatePieChart() whenever transactions change
viewModel.transactions.observe(this, Observer { list ->
    adapter.updateList(list)
    binding.tvSummary.text = "Total: $${String.format("%.2f", viewModel.getTotalAmount())}"
    updatePieChart()
})

      // Default fragment
        openFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> openFragment(HomeFragment())
                R.id.nav_analytics -> openFragment(AnalyticsFragment())
                R.id.nav_add -> {
                    val dialog = AddTransactionDialog()
                    dialog.show(supportFragmentManager, "AddTransactionDialog")
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}







val categories = JsonUtils.loadCategories(this).map { it.name }.toMutableList()
categories.add(0, "All") // Add 'All' at the top for filtering
val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
binding.spinnerCategory.adapter = spinnerAdapter

startActivity(intent)
overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
binding.btnAnalytics.setOnClickListener {
    val intent = Intent(this, AnalyticsActivity::class.java)
    startActivity(intent)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

binding.btnBack.setOnClickListener {
    finish()
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

