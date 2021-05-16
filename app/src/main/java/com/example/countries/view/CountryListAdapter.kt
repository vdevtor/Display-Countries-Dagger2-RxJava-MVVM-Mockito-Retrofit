package com.example.countries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countries.databinding.ItemCountryBinding
import com.example.countries.model.Country
import com.example.countries.utils.getProgressDrawable
import com.example.countries.utils.loadImage

class CountryListAdapter(var countries: ArrayList<Country>) : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountry: List<Country>) {
        countries.clear()
        countries.addAll(newCountry)
        notifyDataSetChanged()
    }

    class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val progressDrawable = getProgressDrawable(itemView.context)

          fun bind(country: Country) = with(binding) {
            name.text = country.countryName
            capital.text = country.capital
            imageView.loadImage(country.flag,progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCountryBinding.inflate(layoutInflater)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size
}