package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 03-01-2018.
 */
class RegisterViewModel : ViewModel() {

    private lateinit var userEntity: UserEntity

    companion object {
        val registerLiveData = MutableLiveData<Boolean>()
    }

    public fun doRegister(context: Context, userEntity: UserEntity) {
        DbHelper.instance.callRegisterMethod(context, userEntity, {
            creationStatus ->
            when (creationStatus) {
                true -> {
                    registerLiveData.postValue(true)
                }
                false -> {
                    registerLiveData.postValue(false)
                }
            }
        })
    }
}