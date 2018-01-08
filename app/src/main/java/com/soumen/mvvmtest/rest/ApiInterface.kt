package com.soumen.mvvmtest.rest

import retrofit2.Call
import retrofit2.Response

/**
 * Created by Soumen on 08-01-2018.
 * A common interface file for service call responses and failures
 * Instead of making the functions generic, make the interface generic
 */
interface ApiInterface<T> {
    public fun onResponse(call: Call<T>?, response: Response<T>?)
    public fun onFailure(call: Call<T>?, t: Throwable)
}