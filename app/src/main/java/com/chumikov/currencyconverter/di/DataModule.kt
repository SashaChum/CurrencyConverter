package com.chumikov.currencyconverter.di

import com.chumikov.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.chumikov.currencyconverter.domain.CurrencyRepository
import dagger.Binds
import dagger.Module


@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: CurrencyRepositoryImpl): CurrencyRepository
}