package com.soumen.mvvmtest.utils

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.Looper
import android.util.Log
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.roomdbops.AppDatabase
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

/**
 * Created by Soumen on 03-01-2018.
 * Singleton class to invoke database related methods
 */
public class DbHelper private constructor() {

    private object Holder {
        val INSTANCE = DbHelper()
    }

    companion object {
        val instance: DbHelper by lazy {
            Holder.INSTANCE
        }
    }

    // This is the callback holder //
    var callBackLocation: DbOperationsInterface? = null

    fun setOnDbOperationDoneListener(callBackLocation: DbOperationsInterface) {
        this.callBackLocation = callBackLocation
    }

    /**
     * Method to invoke the login method
     * @param context
     * @param userId
     * @param password
     */
    fun callLoginMethod(context: Context, userId: String, password: String) {
        var loginResult: UserEntity? = null
        var deferred = async {
            loginResult = AppDatabase.getAppDatabase(context).simpleLoginDao().doLogin(userId, password)
        }
        runBlocking {
            deferred.await()
            if (loginResult == null) {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.DOLOGIN, false)
            } else {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.DOLOGIN, true)
            }
        }
    }

    /**
     * Method to insert an user to db
     * @param context
     * @param userEntity
     */
    fun callRegisterMethod(context: Context, userEntity: UserEntity) {
        var registerResult: Long? = null
        var deferred = async {
            registerResult = AppDatabase.getAppDatabase(context).registerUserDao().registerUser(userEntity)
        }
        runBlocking {
            deferred.await()
            if (registerResult == null) {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.REGISTER, false)
            } else {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.REGISTER, true)
            }
        }
    }

    /**
     * Method to get all user record from room db
     * @param context
     */
    fun callAllUserListMethod(context: Context) {
        var allUserList: LiveData<List<UserEntity>>? = null
        var deferred = async {
            allUserList = AppDatabase.getAppDatabase(context).allUserListDao().getAllUserList()
        }
        runBlocking {
            deferred.await()
            if(allUserList == null) {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.ALLUSERLIST, null)
            } else {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.ALLUSERLIST, allUserList)
            }
        }
    }
}