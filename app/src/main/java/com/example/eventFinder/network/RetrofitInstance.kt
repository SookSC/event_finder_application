package com.example.eventFinder.network

import com.example.eventFinder.network.services.EventService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val PREDICT_HQ_BASE_URL = "https://api.predicthq.com/v1/"
    private const val TICKETMASTER_BASE_URL = "https://app.ticketmaster.com/discovery/v2/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val predictHqRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(PREDICT_HQ_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val ticketMasterRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(TICKETMASTER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val predictHqEventService: EventService by lazy {
        predictHqRetrofit.create(EventService::class.java)
    }

    val ticketMasterEventService: EventService by lazy {
        ticketMasterRetrofit.create(EventService::class.java)
    }
}
