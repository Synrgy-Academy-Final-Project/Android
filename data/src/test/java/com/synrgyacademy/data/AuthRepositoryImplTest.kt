package com.synrgyacademy.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgyacademy.data.local.pref.SessionManager
import com.synrgyacademy.data.remote.retrofit.AuthService
import com.synrgyacademy.data.repository.AuthRepositoryImpl
import com.synrgyacademy.domain.model.auth.UserDataDataModel
import com.synrgyacademy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var sessionManager: SessionManager

    @Mock
    private lateinit var authAPI: AuthService
    private lateinit var repository: AuthRepository

    private val user = UserDataDataModel(
        email = "test@mail",
        token = "token"
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = AuthRepositoryImpl(sessionManager = sessionManager, authService = authAPI)
    }

    @Test
    fun `user login should return true`() = runTest {
        val expected = flow { emit(true) }
        `when`(sessionManager.isLogin()).thenReturn(expected)
        val actual = repository.isLogin()
        assertEquals(expected, actual)
    }

    @Test
    fun `user is not login should return false`() = runTest {
        val expected = flow { emit(false) }
        `when`(sessionManager.isLogin()).thenReturn(expected)
        val actual = repository.isLogin()
        assertEquals(expected, actual)
    }

    @Test
    fun `createSession should delegate to session manager`() = runTest {
        `when`(sessionManager.createSession()).thenReturn(Unit)
        repository.createSession()
        verify(sessionManager).createSession()
    }

    @Test
    fun `getSavedData should return userdata`() = runTest {
        val expected = flowOf(user)
        `when`(sessionManager.getUser()).thenReturn(expected)
        val actual = repository.getUserData()
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun logoutAccount() = runTest {
        `when`(sessionManager.logout()).thenReturn(Unit)
        repository.logout()
        verify(sessionManager).logout()
    }
}