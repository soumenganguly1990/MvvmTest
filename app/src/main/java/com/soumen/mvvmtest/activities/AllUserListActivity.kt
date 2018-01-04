package com.soumen.mvvmtest.activities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.adapters.AllUserAdapter
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.utils.DbHelper
import com.soumen.mvvmtest.viewmodels.AllUserListViewModel
import kotlinx.android.synthetic.main.activity_alluser.*

/**
 * Created by Soumen on 03-01-2018.
 * AppcompatActivity in it's inheritance chain has SupportActivity, which extends LifeCycleOwner, so no need to implement it here
 */
class AllUserListActivity : AppCompatActivity(), DbOperationsInterface {

    lateinit var allUserList: ArrayList<UserEntity>
    lateinit var allUserListViewModel: AllUserListViewModel

    /* recyclerview's adapter declaration */
    lateinit var allUserAdapter: AllUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alluser)
        populateAllUserList()

        allUserListViewModel = ViewModelProviders.of(this@AllUserListActivity).get(AllUserListViewModel::class.java)

        DbHelper.callBackLocation = this@AllUserListActivity
        allUserListViewModel.getAllUserListFromDb(this@AllUserListActivity)
    }

    private fun populateAllUserList() {
        rclAllUsers.layoutManager = LinearLayoutManager(this@AllUserListActivity)
        allUserList = ArrayList<UserEntity>()
        allUserAdapter = AllUserAdapter(this@AllUserListActivity, allUserList)
        rclAllUsers.adapter = allUserAdapter
    }

    override fun <T> onDbOperationsCompleted(whichMethod: MethodNameEnum, result: T) {
        if(whichMethod == MethodNameEnum.REGISTER) {
            // do nothing //
        } else {
            if (result == null) {
                Toast.makeText(this@AllUserListActivity, "Error or no result found", Toast.LENGTH_SHORT).show()
            } else {
                (result as LiveData<List<UserEntity>>).observe(this@AllUserListActivity, object : Observer<List<UserEntity>> {
                    override fun onChanged(t: List<UserEntity>?) {
                        allUserAdapter.addItems(t)
                    }
                })
            }
        }
    }
}