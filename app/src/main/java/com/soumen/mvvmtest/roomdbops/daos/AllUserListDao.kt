package com.soumen.mvvmtest.roomdbops.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.soumen.mvvmtest.roomdbops.entities.UserEntity

/**
 * Created by Soumen on 04-01-2018.
 */
@Dao
interface AllUserListDao {
    @Query("SELECT * FROM user")
    fun getAllUserList(): LiveData<List<UserEntity>>
}