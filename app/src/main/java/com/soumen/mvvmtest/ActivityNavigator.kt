package com.soumen.mvvmtest

import android.app.Activity
import android.content.Intent
import com.soumen.mvvmtest.activities.AllUserListActivity

/**
 * Created by Soumen on 10-01-2018.
 */
class ActivityNavigator(val activity: Activity) {

    fun navigateToAllUserListPage() {
        var i: Intent = Intent(activity, AllUserListActivity::class.java)
        startActivity(i)
    }

    fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }
}