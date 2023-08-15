package com.apex.codeassesment
import com.apex.codeassesment.api.ApiService
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        remoteDataSource = RemoteDataSource(apiService)
    }


    @Test
    fun loadUser_returnsUser() = runBlocking {
        val response = remoteDataSource.loadUser()

        assert(response != null)
    }

    @Test
    fun loadUsers_returnsListOfUsers() = runBlocking {
        val response = remoteDataSource.loadUsers()

        assert(response != null)
    }
}
