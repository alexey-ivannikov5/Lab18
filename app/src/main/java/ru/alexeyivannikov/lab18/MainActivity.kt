package ru.alexeyivannikov.lab18

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import ru.alexeyivannikov.lab18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CurrencyListAdapter()
        binding.rvCurrencyList.adapter = adapter
        viewModel.screenState.observe(this) {
            when(it) {
                is ScreenState.Init -> {}
                is ScreenState.Loading ->{
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is ScreenState.Data -> {
                    binding.pbLoading.visibility = View.INVISIBLE
                    adapter.submitList(it.data)
                    Log.d("XML_TEST", it.data.toString())
                }
            }
        }

        binding.btLoad.setOnClickListener{
            viewModel.loadCurrencies()
        }
    }
}