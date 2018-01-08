package com.soumen.mvvmtest.rest

import com.soumen.mvvmtest.models.Country
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Soumen on 05-01-2018.
 */
interface ApiCalls {

    @GET("v2/all")
    fun getAllCountryList(): Call<List<Country>>
}