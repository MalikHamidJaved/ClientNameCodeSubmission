package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// TODO (2 points) : Add tests
// Done (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings
//class UserRepository @Inject constructor(
//  private val localDataSource: localdatasource,
//  private val remoteDataSource: RemoteDataSource
//) {
//
//  private val savedUser = AtomicReference(User())
//
//  fun getSavedUser() = localDataSource.loadUser()!!
//
//  fun getUser(forceUpdate: Boolean): User {
//    if (forceUpdate) {
//      val user = remoteDataSource.LoadUser()
//      localDataSource.saveUser(user)
//      savedUser.set(user)
//    }
//    return savedUser.get()
//  }
//
//  fun getUsers() = remoteDataSource.loadUsers()
//}


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers

class UserRepository @Inject constructor(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
) : IUserRepository {

  override fun getSavedUser(): Flow<User> {
    return localDataSource.loadUserFlow()
      .flowOn(Dispatchers.IO)
  }


  override fun getUser(forceUpdate: Boolean): Flow<User> {
    if (forceUpdate) {
      return remoteDataSource.loadUser()
        .map { user ->
          localDataSource.saveUser(user!!)
          user
        }
    }
    return localDataSource.loadUserFlow()
  }

  override fun getUsers(): Flow<List<User>?> {
    return remoteDataSource.loadUsers()
      .map { users ->
        // Handle success and update local data source
        users
      }
      .flowOn(Dispatchers.IO)
  }
}


interface IUserRepository {
  fun getSavedUser(): Flow<User>
  fun getUser(forceUpdate: Boolean): Flow<User>
  fun getUsers(): Flow<List<User>?>
}
