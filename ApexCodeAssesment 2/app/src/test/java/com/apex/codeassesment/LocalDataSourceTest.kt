package com.apex.codeassesment

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.model.User
import com.squareup.moshi.Moshi
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        preferencesManager = mockk()
        moshi = mockk()
        localDataSource = LocalDataSource(preferencesManager, moshi)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun invalidUserTest() = runBlocking {
        // Given
        val serializedUser = "invalidSerializedUser"
        coEvery { preferencesManager.loadUser() } returns serializedUser
        every { moshi.adapter<User>(User::class.java) } returns mockk()
        try {
            every { moshi.adapter<User>(User::class.java).fromJson(serializedUser) } throws Exception()
        }catch (e:Exception ){
            assert(true)
        }

        // When
        val resultFlow = localDataSource.loadUserFlow()

        // Then
        resultFlow.collect { resultUser ->
            assert(resultUser != null)
        }
    }
}
