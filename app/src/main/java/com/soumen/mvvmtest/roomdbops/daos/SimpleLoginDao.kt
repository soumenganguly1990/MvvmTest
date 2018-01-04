package com.soumen.mvvmtest.roomdbops.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.soumen.mvvmtest.roomdbops.entities.UserEntity

/**
 * Created by Soumen on 03-01-2018.
 */
@Dao
interface SimpleLoginDao {
    @Query("SELECT * FROM user WHERE userId=:userId AND password=:password")
    fun doLogin(userId: String, password: String): UserEntity
}