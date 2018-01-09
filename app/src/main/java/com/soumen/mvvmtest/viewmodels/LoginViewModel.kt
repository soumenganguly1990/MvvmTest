package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 03-01-2018.
 */
class LoginViewModel : ViewModel() {

    private lateinit var userId: String
    private lateinit var password: String

    companion object {
        val loginStatusLiveData = MutableLiveData<Boolean>()
    }

    public fun setUserId(userId: String) {
        this.userId = userId
    }

    public fun setPassword(password: String) {
        this.password = password
    }

    public fun doLoginWithLambda(context: Context) {
        DbHelper.instance.callLoginMethod(context, userId, password, {
            loginStatus ->
            when (loginStatus) {
                true -> {
                    loginStatusLiveData.postValue(true)
                }
                false -> {
                    loginStatusLiveData.postValue(false)
                }
                else -> {
                    Log.e("situation", "abnormal")
                }
            }
        })
    }
}