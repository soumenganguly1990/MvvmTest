package com.soumen.mvvmtest.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.adapters.AllUserAdapter
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.viewmodels.AllUserListViewModel
import com.soumen.mvvmtest.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.activity_alluser.*

/**
 * Created by Soumen on 03-01-2018.
 * AppcompatActivity in it's inheritance chain has SupportActivity, which extends LifeCycleOwner, so no need to implement it here
 */
class AllUserListActivity : AppCompatActivity(), DbOperationsInterface, View.OnClickListener {

    lateinit var allUserList: ArrayList<UserEntity>
    lateinit var allUserListViewModel: AllUserListViewModel
    lateinit var registerUserViewModel: RegisterViewModel

    /* recyclerview's adapter declaration */
    lateinit var allUserAdapter: AllUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alluser)
        populateAllUserList()

        allUserListViewModel = ViewModelProviders.of(this@AllUserListActivity).get(AllUserListViewModel::class.java)
        registerUserViewModel = ViewModelProviders.of(this@AllUserListActivity).get(RegisterViewModel::class.java)

        AllUserListViewModel.allUserListViewModelLivedata.observe(this@AllUserListActivity, Observer {
            allUserList ->
            populateAllUserList()
            allUserList!!.observe(this@AllUserListActivity, Observer<List<UserEntity>> {
                t ->
                allUserAdapter.addItems(t)
            })
        })
        allUserListViewModel.getAllUserListFromDb(this@AllUserListActivity)

        setUpListeners()
    }

    /**
     * Populates an empty recyclerview
     */
    private fun populateAllUserList() {
        rclAllUsers.layoutManager = LinearLayoutManager(this@AllUserListActivity)
        allUserList = ArrayList<UserEntity>()
        allUserAdapter = AllUserAdapter(this@AllUserListActivity, allUserList)
        rclAllUsers.adapter = allUserAdapter
    }

    private fun setUpListeners() {
        btnCreateUser.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v == btnCreateUser) {
            getUserDetailsAndSaveToRoom()
        }
    }

    /**
     * Gets user details from fields and creates a new record
     */
    private fun getUserDetailsAndSaveToRoom() {
        var userEntity: UserEntity = UserEntity(edtRegId.text.toString(),
                edtRegPassword.text.toString(), edtRegFname.text.toString(), edtRegLname.text.toString())
        registerUserViewModel.doRegister(this@AllUserListActivity, userEntity, this@AllUserListActivity)
    }

    /**
     * Just clears the editable fields
     */
    private fun clearFields() {
        edtRegId.text.clear()
        edtRegPassword.text.clear()
        edtRegFname.text.clear()
        edtRegLname.text.clear()
    }

    override fun <T> onDbOperationsCompleted(whichMethod: MethodNameEnum, result: T) {
        if(whichMethod == MethodNameEnum.REGISTER) {
            if(result as Boolean) {
                Toast.makeText(this@AllUserListActivity, "User Created, Check The Updated List", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this@AllUserListActivity, "User Could Not Be Created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}