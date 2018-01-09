package com.soumen.mvvmtest.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.utils.DbHelper

/**
 * Created by Soumen on 04-01-2018.
 */
class AllUserListViewModel : ViewModel() {

    companion object {
        val allUserListViewModelLivedata = MutableLiveData<LiveData<List<UserEntity>>>()
    }

    public fun getAllUserListFromDb(context: Context) {
        DbHelper.instance.callAllUserListMethod(context, {
            allUserList ->
            if(allUserList == null) {
                allUserListViewModelLivedata.postValue(null)
            } else {
                allUserListViewModelLivedata.postValue(allUserList)
            }
        })
    }
}