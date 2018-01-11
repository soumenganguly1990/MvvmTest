package com.soumen.mvvmtest.utils

import android.arch.lifecycle.LiveData
import android.content.Context
import com.soumen.mvvmtest.roomdbops.AppDatabase
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
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

    /**
     * Method to invoke the login method
     * @param context
     * @param userId
     * @param password
     */
    fun callLoginMethod(context: Context, userId: String, password: String, loginStatus: (Boolean) -> Unit) {
        var loginResult: UserEntity? = null
        var deferred = async {
            loginResult = AppDatabase.getAppDatabase(context).simpleLoginDao().doLogin(userId, password)
        }
        runBlocking {
            deferred.await()
            if (loginResult == null) {
                loginStatus.invoke(false)
            } else {
                loginStatus.invoke(true)
            }
        }
    }

    /**
     * Method to insert an user entity to db
     * @param context
     * @param userEntity
     */
    fun callRegisterMethod(context: Context, userEntity: UserEntity, creationStatus: (Boolean) -> Unit) {
        var registerResult: Long? = null
        var deferred = async {
            registerResult = AppDatabase.getAppDatabase(context).registerUserDao().registerUser(userEntity)
        }
        runBlocking {
            deferred.await()
            if (registerResult == null) {
                creationStatus.invoke(false)
            } else {
                creationStatus.invoke(true)
            }
        }
    }

    /**
     * Method to get all user record from room db
     * @param context
     */
    fun callAllUserListMethod(context: Context, userList: (LiveData<List<UserEntity>>?) -> Unit) {
        var allUserList: LiveData<List<UserEntity>>? = null
        var deferred = async {
            allUserList = AppDatabase.getAppDatabase(context).allUserListDao().getAllUserList()
        }
        runBlocking {
            deferred.await()
            if(allUserList == null) {
                userList.invoke(null)
            } else {
                userList.invoke(allUserList)
            }
        }
    }
}