package com.apex.codeassesment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.IUserRepository
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: IUserRepository) : ViewModel() {

    private val _usersData = MutableLiveData<List<User>>()
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User>
        get() = _userData
    val usersData: LiveData<List<User>>
        get() = _usersData

    // Method to refresh user data
    fun refreshUser(forceUpdate: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
             userRepository.getUser(forceUpdate).collect{
                 viewModelScope.launch {
                     _userData.value = it
                 }

            }
        }
    }

    fun fetchUsersList(){
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.getUsers().collect { users ->
                viewModelScope.launch {
                    _usersData.value = users
                }
            }
        }

    }
}