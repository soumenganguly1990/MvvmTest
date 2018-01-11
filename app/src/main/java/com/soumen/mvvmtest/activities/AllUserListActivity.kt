package com.soumen.mvvmtest.activities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.adapters.AllUserAdapter
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.viewmodels.AllUserListViewModel
import com.soumen.mvvmtest.viewmodels.RegisterViewModel
import com.soumen.mvvmtest.views.AllUserListView
import kotlinx.android.synthetic.main.activity_alluser.*

/**
 * Created by Soumen on 03-01-2018.
 * AppcompatActivity in it's inheritance chain has SupportActivity, which extends LifeCycleOwner, so no need to implement it here
 */
class AllUserListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var allUserList: ArrayList<UserEntity>
    lateinit var allUserListViewModel: AllUserListViewModel
    lateinit var registerUserViewModel: RegisterViewModel

    lateinit var allUserListView: AllUserListView

    lateinit var registerUserLiveData: MutableLiveData<Boolean>

    /* recyclerview's adapter declaration */
    lateinit var allUserAdapter: AllUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alluser)

        allUserListViewModel = ViewModelProviders.of(this@AllUserListActivity).get(AllUserListViewModel::class.java)
        registerUserViewModel = ViewModelProviders.of(this@AllUserListActivity).get(RegisterViewModel::class.java)
        allUserListView = AllUserListView(findViewById(android.R.id.content))

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
        allUserList = ArrayList<UserEntity>()
        allUserAdapter = AllUserAdapter(this@AllUserListActivity, allUserList)
        allUserListView.populateRecyclerViewWithUserList(this, allUserList, allUserAdapter)
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
        var userEntity: UserEntity = UserEntity(allUserListView.getRegId(),
                allUserListView.getRegPassword(), allUserListView.getRegFname(), allUserListView.getRegLname())
        registerUserViewModel.doRegister(this@AllUserListActivity, userEntity)
        RegisterViewModel.registerLiveData.observe(this@AllUserListActivity, Observer {
            registrationStatus ->
            when (registrationStatus) {
                true -> {
                    allUserListView.userSuccessfullyCreated(this@AllUserListActivity)
                }
                false -> {
                    allUserListView.userCouldNotBeCreated(this@AllUserListActivity)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}