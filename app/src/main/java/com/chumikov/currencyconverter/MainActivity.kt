package com.chumikov.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.chumikov.currencyconverter.data.network.ExchangeratesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://v6.exchangerate-api.com/v6/")
            .client(client)
            .build()
        val api = retrofit.create(ExchangeratesApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val currencies = api.getAllCurrencies(API_KEY)
            Log.d("TryRetrofit", "onCreate: $currencies")
        }

    }

    companion object {
        private const val API_KEY = "aad958292bc85ba014d0578e"
    }
}