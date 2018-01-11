package com.soumen.mvvmtest.views

import android.support.v7.widget.AppCompatButton
import com.soumen.mvvmtest.BaseView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.soumen.mvvmtest.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Soumen on 10-01-2018.
 */
class MainActivityView(view: View) : BaseView(view) {

    val edtUserId: EditText? by lazy { getViewComponent(R.id.edtUserId) as EditText? }
    val edtUserPassword: EditText? by lazy { getViewComponent(R.id.edtUserPassword) as EditText? }
    val btnLogin: AppCompatButton? by lazy { getViewComponent(R.id.btnLogin) as AppCompatButton? }
    val btnShowList: AppCompatButton? by lazy { getViewComponent(R.id.btnShowList) as AppCompatButton? }
    val btnTestStetho: AppCompatButton? by lazy { getViewComponent(R.id.btnTestStetho) as AppCompatButton? }
    val txtId: TextView? by lazy { getViewComponent(R.id.txtId) as TextView? }

    /**
     * This is just for some initial manipulation, this is not really a required function
     */
    fun initialViewSetup() {
        edtUserId!!.setText("1001")
        edtUserPassword!!.setText("123")
        txtId!!.visibility = View.GONE
    }

    fun getUserId(): String {
        return edtUserId!!.text.toString()
    }

    fun getUserPassword(): String {
        return edtUserPassword!!.text.toString()
    }

    fun updateUiOnLoginSuccess() {
        txtId!!.visibility = View.VISIBLE
        txtId!!.text = "Login Successful"
        btnShowList!!.visibility = View.VISIBLE
    }

    fun updateUiOnLoginFailure() {
        txtId!!.visibility = View.VISIBLE
        txtId!!.text = "Login Failed!!"
        btnShowList!!.visibility = View.GONE
    }
}