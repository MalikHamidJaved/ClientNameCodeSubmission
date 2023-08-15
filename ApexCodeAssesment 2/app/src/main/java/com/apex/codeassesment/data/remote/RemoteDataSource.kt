package com.apex.codeassesment.data.remote


import com.apex.codeassesment.api.ApiService
import com.apex.codeassesment.api.GetUsersResponse
import com.apex.codeassesment.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

  fun loadUser(): Flow<User?> = flow {
    val response: Response<GetUsersResponse> = apiService.getUser().execute()
    if (response.isSuccessful) {
      response.body()?.results?.let {
        if(it.isNotEmpty())
        emit(it[0])
      }
    } else {
      emit(null)
    }
  }

  fun loadUsers(): Flow<List<User>?> = flow {
    val response: Response<GetUsersResponse> = apiService.getUsers().execute()
    if (response.isSuccessful) {
      response.body()?.results?.let {
        emit(it)
      }
    } else {
      emit(null)
    }
  }
}
