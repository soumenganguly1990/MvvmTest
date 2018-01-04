package com.soumen.mvvmtest.callbackinterfaces

import com.soumen.mvvmtest.extras.MethodNameEnum

/**
 * Created by Soumen on 03-01-2018.
 */
interface DbOperationsInterface {

    // Make the function generic so that it can return any type of data from here //
    public fun<T> onDbOperationsCompleted(whichMethod: MethodNameEnum, result: T)
}