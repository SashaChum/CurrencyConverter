package com.chumikov.currencyconverter.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chumikov.currencyconverter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val api = ApiFactory.exchageRateApi
//        CoroutineScope(Dispatchers.IO).launch {
//            val currencies = api.getAllCurrencies(API_KEY)
//            Log.d("TryRetrofit", "onCreate: $currencies")
//        }

    }

}