package com.soumen.mvvmtest.roomdbops.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Soumen on 03-01-2018.
 */
@Entity(tableName = "user")
data class UserEntity(@PrimaryKey(autoGenerate = false) var userId: String,
                      @ColumnInfo(name = "password") var password: String,
                      @ColumnInfo(name = "fname") var fname: String,
                      @ColumnInfo(name = "lname") var lname: String)