package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 03-01-2018.
 */
class LoginViewModel : ViewModel() {

    private lateinit var userId: String
    private lateinit var password: String

    public fun setUserId(userId: String) {
        this.userId = userId
    }

    public fun setPassword(password: String) {
        this.password = password
    }

    public fun doLogin(context: Context, dbOperationsInterface: DbOperationsInterface) {
        DbHelper.instance.callLoginMethod(context, userId, password, dbOperationsInterface)
    }
}