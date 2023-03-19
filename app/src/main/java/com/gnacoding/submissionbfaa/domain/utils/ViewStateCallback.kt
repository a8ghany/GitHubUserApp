package com.gnacoding.submissionbfaa.domain.utils

interface ViewStateCallback<T> {
    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)
}