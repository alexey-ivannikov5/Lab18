package ru.alexeyivannikov.lab18

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alexeyivannikov.lab18.databinding.CurrencyItemBinding
import java.util.Locale

class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyItemViewHolder>() {

    private var currencyList: List<CurrencyItem> = listOf()

    fun submitList(currencyList: List<CurrencyItem>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
    }

    class CurrencyItemViewHolder(val binding: CurrencyItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val binding = CurrencyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        val currency = currencyList[position]
        with(holder.binding) {
            tvCurrencyName.text = currency.name
            tvCurrencyPrice.text = String.format(Locale.getDefault(), "%.3f", currency.price)
        }
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }
}