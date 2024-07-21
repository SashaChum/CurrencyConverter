package com.chumikov.currencyconverter.di

import android.content.Context
import com.chumikov.currencyconverter.presentation.CalculationScreenFragment
import com.chumikov.currencyconverter.presentation.MainScreenFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ApiModule::class, DataModule::class])
interface ApplicationComponent {

    fun inject(fragment: MainScreenFragment)
//    fun inject(fragment: CalculationScreenFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}