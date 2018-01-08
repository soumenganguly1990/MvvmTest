package com.soumen.mvvmtest.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.soumen.mvvmtest.R
import com.soumen.mvvmtest.callbackinterfaces.DbOperationsInterface
import com.soumen.mvvmtest.extras.MethodNameEnum
import com.soumen.mvvmtest.models.Country
import com.soumen.mvvmtest.callbackinterfaces.ApiInterface
import com.soumen.mvvmtest.viewmodels.CallAServiceViewModel
import com.soumen.mvvmtest.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Soumen on 02-01-2018.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener, DbOperationsInterface {

    /* The viewmodel object(s) */
    lateinit var loginViewModel: LoginViewModel
    lateinit var callAServiceViewModel: CallAServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProviders.of(this@MainActivity).get(LoginViewModel::class.java)
        callAServiceViewModel = ViewModelProviders.of(this@MainActivity).get(CallAServiceViewModel::class.java)
        setUpListeners()

        edtUserId.setText("1001")
        edtUserPassword.setText("123")

        //createAnUserOnlyFirstTime()   // uncomment this function if running for the first time
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * Use this function only for the first time, because creation of user is not given and it creates an user
     * Just uncomment it and run once. If you are done then use as per your wish and comment it again
     */
    /*private fun createAnUserOnlyFirstTime() {
        DbHelper.instance.setOnDbOperationDoneListener(this)
        var registerUserViewModel = ViewModelProviders.of(this@MainActivity).get(RegisterViewModel::class.java)
        var userEntity: UserEntity = UserEntity("1001", "123", "sam", "tan")
        registerUserViewModel.doRegister(this@MainActivity, userEntity)
    }*/

    private fun setUpListeners() {
        btnLogin.setOnClickListener(this)
        btnShowList.setOnClickListener(this)
        btnTestStetho.setOnClickListener(this)
    }

    /**
     * Updates UI on login result retrieval
     */
    private fun validateUserLogin() {
        loginViewModel.setUserId(edtUserId.text.toString())
        loginViewModel.setPassword(edtUserPassword.text.toString())
        loginViewModel.doLogin(this@MainActivity, this@MainActivity)
    }

    /**
     * Calls the country service (3rd party open source, used here just for checking)
     */
    private fun callCountryService() {
        callAServiceViewModel.callTheCountryWebservice(object : ApiInterface<List<Country>> {
            override fun onResponse(call: Call<List<Country>>?, response: Response<List<Country>>?) {
                if(response != null) {
                    Log.e("The result", response.body().toString())
                }
            }
            override fun onFailure(call: Call<List<Country>>?, t: Throwable) {
                Log.e("Error result", t.message)
            }
        })
    }

    override fun onClick(v: View?) {
        if (v == btnLogin) {
            validateUserLogin()
        }
        if (v == btnShowList) {
            startActivity(Intent(this@MainActivity, AllUserListActivity::class.java))
        }
        if(v == btnTestStetho) {
            callCountryService()
        }
    }

    override fun <T> onDbOperationsCompleted(methodNameEnum: MethodNameEnum, result: T) {
        if (methodNameEnum == MethodNameEnum.DOLOGIN) {
            txtId.visibility = View.VISIBLE
            if (result as Boolean) {
                btnShowList.visibility = View.VISIBLE
                txtId.text = "Success"
            } else {
                txtId.text = "Login failed"
                btnShowList.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}