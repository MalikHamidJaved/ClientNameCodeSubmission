package com.apex.codeassesment

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.apex.codeassesment.data.local.PreferencesManager
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesManagerInstrumentedTest {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        preferencesManager = PreferencesManager()
    }

    @After
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun saveUser_loadUser_ShouldRetrieveSavedUser() {
        // Given
        val user = "sampleUser"

        // When
        preferencesManager.saveUser(user)
        val loadedUser = preferencesManager.loadUser()

        // Then
        assertEquals(user, loadedUser)
    }
}
