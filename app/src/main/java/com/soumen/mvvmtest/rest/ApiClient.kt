package com.soumen.mvvmtest.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

/**
 * Created by Soumen on 05-01-2018.
 */
class ApiClient {

    companion object {
        val BASE_URL = "https://restcountries.eu/rest/"
        private var retrofit: Retrofit? = null
        private var okHttpClient: OkHttpClient? = null

        fun getClient(): Retrofit {
            if (retrofit == null) {
                okHttpClient = OkHttpClient.Builder()
                        .addNetworkInterceptor(StethoInterceptor())
                        .build()
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build()
            }
            return retrofit!!
        }
    }
}