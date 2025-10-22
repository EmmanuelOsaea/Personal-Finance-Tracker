package com.example.financetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financetracker.databinding.ActivityMainBinding
import com.example.financetracker.ui.TransactionViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = TransactionViewModel(this)

        // TODO: Display transaction list
        // TODO: Add new transaction
        // TODO: Delete transaction
    }
}
