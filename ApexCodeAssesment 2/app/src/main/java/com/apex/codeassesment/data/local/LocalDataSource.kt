package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val moshi: Moshi
) {

    fun loadUserFlow(): Flow<User> {
        val serializedUser = preferencesManager.loadUser()
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        try {
            val user = jsonAdapter.fromJson(serializedUser)
            return flow {
                emit(user ?: User.createRandom())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return User.createRandomFlow()
            }
        }


    fun saveUser(user: User) {
        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
        val serializedUser = jsonAdapter.toJson(user)
        preferencesManager.saveUser(serializedUser)
    }
}



