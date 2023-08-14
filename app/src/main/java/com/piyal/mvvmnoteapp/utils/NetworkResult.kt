package com.piyal.mvvmnoteapp.utils

sealed class NetworkResult <T>(val date:T? =null,val message: String?=null){

    class Success<T>(data : T) : NetworkResult<T>(data)
    class Error<T>(message: String?,date: T?= null): NetworkResult<T>(date, message)
    class Loading<T> : NetworkResult<T>()
}