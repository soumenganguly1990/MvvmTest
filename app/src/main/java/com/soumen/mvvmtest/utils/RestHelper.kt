package com.soumen.mvvmtest.utils

import com.soumen.mvvmtest.models.Country
import com.soumen.mvvmtest.rest.ApiCalls
import com.soumen.mvvmtest.rest.ApiClient
import com.soumen.mvvmtest.callbackinterfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Soumen on 08-01-2018.
 */
class RestHelper {

    private object Holder {
        val INSTANCE = RestHelper()
    }

    companion object {
        val instance: RestHelper by lazy {
            Holder.INSTANCE
        }
        var apiService = ApiClient.getClient().create(ApiCalls::class.java)
    }

    /**
     * This method will call the country details service
     * @param apiInterfaceCallbackLocation the callback listener location
     */
    public fun callCountryMethod(apiInterfaceCallbackLocation: ApiInterface<List<Country>>) {
        var result: String? = null
        var call = apiService.getAllCountryList()
        call.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>?, response: Response<List<Country>>?) {
                result = response!!.body().toString()
                apiInterfaceCallbackLocation!!.onResponse(call, response)
            }
            override fun onFailure(call: Call<List<Country>>?, t: Throwable?) {
                apiInterfaceCallbackLocation!!.onFailure(call, t!!)
            }
        })
    }
}