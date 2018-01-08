package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 04-01-2018.
 */
class AllUserListViewModel : ViewModel() {
    public fun getAllUserListFromDb(context: Context, dbOperationsInterface: DbOperationsInterface) {
        DbHelper.instance.callAllUserListMethod(context, dbOperationsInterface)
    }
}