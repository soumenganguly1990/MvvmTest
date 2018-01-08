package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 03-01-2018.
 */
class RegisterViewModel : ViewModel() {

    private lateinit var userEntity: UserEntity

    public fun doRegister(context: Context, userEntity: UserEntity, dbOperationsInterface: DbOperationsInterface) {
        DbHelper.instance.callRegisterMethod(context, userEntity, dbOperationsInterface)
    }
}