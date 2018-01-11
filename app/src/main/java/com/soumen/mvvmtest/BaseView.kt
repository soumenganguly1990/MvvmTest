package com.soumen.mvvmtest

import android.view.View

/**
 * Created by Soumen on 10-01-2018.
 */
open class BaseView(val view: View) {
    fun getViewComponent(id: Int): View? {
        return view.findViewById(id)!!
    }
}