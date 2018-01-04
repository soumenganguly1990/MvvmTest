package com.soumen.mvvmtest.utils

import android.content.Context
import android.util.Log
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.roomdbops.AppDatabase
import com.soumen.mvvmtest.roomdbops.entities.UserEntity

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
        var loginResult = AppDatabase.getAppDatabase(context).simpleLoginDao().doLogin(userId, password)
        if (loginResult == null) {
            callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.DOLOGIN, false)
        } else {
            if(callBackLocation == null) {
                Log.e("omg", "this is null!!")
            }
            callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.DOLOGIN, true)
        }
    }

    /**
     * Method to insert an user to db
     * @param context
     * @param userEntity
     */
    fun callRegisterMethod(context: Context, userEntity: UserEntity) {
        try {
            var registerResult = AppDatabase.getAppDatabase(context).registerUserDao().registerUser(userEntity)
            if (registerResult >= 1L) {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.REGISTER, true)
            } else {
                callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.REGISTER, false)
            }
        } catch (e: Exception) {
            callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.REGISTER, false)
        }
    }

    /**
     * Method to get all user record from room db
     * @param context
     */
    fun callAllUserListMethod(context: Context) {
        try {
            var allUserList = AppDatabase.getAppDatabase(context).allUserListDao().getAllUserList()
            callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.ALLUSERLIST, allUserList)
        } catch (e: Exception) {
            e.printStackTrace()
            callBackLocation!!.onDbOperationsCompleted(MethodNameEnum.ALLUSERLIST, null)
        }
    }
}