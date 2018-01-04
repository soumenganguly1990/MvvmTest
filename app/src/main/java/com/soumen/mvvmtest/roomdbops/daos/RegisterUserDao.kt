package com.soumen.mvvmtest.roomdbops.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.soumen.mvvmtest.roomdbops.entities.UserEntity

/**
 * Created by Soumen on 03-01-2018.
 */
@Dao
interface RegisterUserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registerUser(userEntity: UserEntity): Long
}