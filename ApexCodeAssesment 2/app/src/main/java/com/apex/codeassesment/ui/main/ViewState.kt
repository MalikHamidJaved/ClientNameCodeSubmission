package com.apex.codeassesment.ui.main

import com.apex.codeassesment.data.model.User

sealed class ViewState {
    data class Loading(val isLoading: Boolean) : ViewState()
    data class Error(val message: String) : ViewState()
    data class UserDetail(val user: User) : ViewState()
}