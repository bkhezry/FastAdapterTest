package com.github.bkhezry.fastadaptertest.util

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
    companion object {
        private const val BASE_URL: String = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"
        private lateinit var retrofit: Retrofit
        private const val REQUEST_TIMEOUT = 10
        private lateinit var okHttpClient: OkHttpClient

        fun getClient(): Retrofit? {
            initOkHttp()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
        }

        private fun initOkHttp() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(interceptor)

            okHttpClient = httpClient.build()
        }
    }

}
