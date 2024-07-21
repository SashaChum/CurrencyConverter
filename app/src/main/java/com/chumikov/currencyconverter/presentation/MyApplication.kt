package com.chumikov.currencyconverter.presentation

import android.app.Application
import com.chumikov.currencyconverter.di.DaggerApplicationComponent

class MyApplication : Application() {

    val component = DaggerApplicationComponent.factory().create()
}