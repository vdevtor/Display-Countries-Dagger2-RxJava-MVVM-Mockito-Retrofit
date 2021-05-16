package com.example.countries.model

import com.example.countries.di.DaggerApiComponents
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {
    @Inject
    lateinit var  api : CountryApi

    init {
        DaggerApiComponents.create().inject(this)
    }

    fun getCountries(): Single<List<Country>>{
        return api.getCountries()
    }
}