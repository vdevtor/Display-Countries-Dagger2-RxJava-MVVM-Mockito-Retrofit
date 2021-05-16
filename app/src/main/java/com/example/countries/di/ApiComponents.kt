package com.example.countries.di

import com.example.countries.model.CountriesService
import com.example.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponents {
    fun inject(service: CountriesService)
    fun inject(viewModel: ListViewModel)
}