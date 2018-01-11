package com.soumen.mvvmtest.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.soumen.mvvmtest.BaseView
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.adapters.AllUserAdapter
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import kotlinx.android.synthetic.main.activity_alluser.*

/**
 * Created by Soumen on 11-01-2018.
 */
class AllUserListView(view: View) : BaseView(view) {

    val rclAllUsers: RecyclerView? by lazy { getViewComponent(R.id.rclAllUsers) as RecyclerView }
    val edtRegId: EditText? by lazy { getViewComponent(R.id.edtRegId) as EditText }
    val edtRegPassword: EditText? by lazy { getViewComponent(R.id.edtRegPassword) as EditText }
    val  edtRegFname: EditText? by lazy { getViewComponent(R.id.edtRegFname) as EditText }
    val edtRegLname: EditText? by lazy { getViewComponent(R.id.edtRegLname) as EditText }

    public fun populateRecyclerViewWithUserList(context: Context, allUserList: List<UserEntity>, allUserAdapter: AllUserAdapter) {
        rclAllUsers!!.layoutManager = LinearLayoutManager(context)
        rclAllUsers!!.adapter = allUserAdapter
    }

    public fun resetFields() {
        edtRegId!!.text.clear()
        edtRegPassword!!.text.clear()
        edtRegFname!!.text.clear()
        edtRegLname!!.text.clear()
    }

    public fun getRegId(): String {
        return edtRegId!!.text.toString()
    }

    public fun getRegPassword(): String {
        return edtRegPassword!!.text.toString()
    }

    public fun getRegFname(): String {
        return edtRegFname!!.text.toString()
    }

    public fun getRegLname(): String {
        return edtRegLname!!.text.toString()
    }

    public fun userSuccessfullyCreated(context: Context) {
        displayToast(context, "User created, check list")
        resetFields()
    }

    public fun userCouldNotBeCreated(context: Context) {
        displayToast(context, "User Could Not Be Created!!!")
    }

    private fun displayToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}