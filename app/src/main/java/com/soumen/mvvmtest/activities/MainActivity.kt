package com.soumen.mvvmtest.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.roomdbops.AppDatabase
import com.soumen.mvvmtest.roomdbops.daos.AllUserListDao
import com.soumen.mvvmtest.roomdbops.entities.UserEntity
import com.soumen.mvvmtest.utils.DbHelper
import com.soumen.mvvmtest.viewmodels.LoginViewModel
import com.soumen.mvvmtest.viewmodels.RegisterViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Soumen on 02-01-2018.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, DbOperationsInterface {

    // The viewmodel object(s) //
    lateinit var loginViewModel: LoginViewModel
    lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProviders.of(this@MainActivity).get(LoginViewModel::class.java)
        registerViewModel = ViewModelProviders.of(this@MainActivity).get(RegisterViewModel::class.java)
        setUpListeners()

        edtUserId.setText("1001")
        edtUserPassword.setText("123")

        // set db operation related callbacks to this activity for now //
        DbHelper.callBackLocation = this
    }

    private fun setUpListeners() {
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
        btnShowList.setOnClickListener(this)
    }

    /**
     * Updates UI on login result retrieval
     */
    private fun updateUiOnLoginResult() {
        loginViewModel.setUserId(edtUserId.text.toString())
        loginViewModel.setPassword(edtUserPassword.text.toString())
        loginViewModel.doLogin(this@MainActivity)
    }

    /**
     * Registers an user to room db
     */
    private fun registerUserToRoomDb() {
        var userEntity: UserEntity = UserEntity("1002", "123", "lam", "gam")
        registerViewModel.doRegister(this@MainActivity, userEntity)
    }

    override fun onClick(v: View?) {
        if(v == btnLogin) {
            updateUiOnLoginResult()
        }
        if(v == btnRegister) {
            registerUserToRoomDb()
        }
        if(v == btnShowList) {
            startActivity(Intent(this@MainActivity, AllUserListActivity::class.java))
        }
    }

    override fun <T> onDbOperationsCompleted(methodNameEnum: MethodNameEnum, result: T) {
        if(methodNameEnum == MethodNameEnum.DOLOGIN) {
            txtId.visibility = View.VISIBLE
            if (result as Boolean) {
                btnShowList.visibility = View.VISIBLE
                txtId.text = "Success"
            } else {
                txtId.text = "Login failed"
                btnShowList.visibility = View.GONE
            }
        }
        if(methodNameEnum == MethodNameEnum.REGISTER) {
            if(result as Boolean) {
                Toast.makeText(this@MainActivity, "Registration successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        // nullify //
        AppDatabase.destroyInstance()
        // unset listener //
        DbHelper.callBackLocation = null
        super.onDestroy()
    }
}