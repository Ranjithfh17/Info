package com.fh.info.utils

sealed class AppResource<T>(
     val data:T?=null,
     val message:String?=null
 ) {
    class Loading<T> : AppResource<T>()
    class Error<T>(message: String,data:T?=null):AppResource<T>(data, message)
    class Success<T>(data: T):AppResource<T>(data)

}