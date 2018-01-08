package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.ViewModel
import com.soumen.mvvmtest.models.Country
import com.soumen.mvvmtest.callbackinterfaces.ApiInterface
import com.soumen.mvvmtest.utils.RestHelper

/**
 * Created by Soumen on 05-01-2018.
 */
class CallAServiceViewModel : ViewModel() {
    public fun callTheCountryWebservice(apiInterface: ApiInterface<List<Country>>) {
        RestHelper.instance.callCountryMethod(apiInterface)
    }
}