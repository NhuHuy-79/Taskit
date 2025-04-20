package badang.android.taskit.feature_auth.domain.repository

import badang.android.taskit.feature_auth.domain.model.User
import badang.android.taskit.feature_auth.utils.AuthResult

interface AuthRepository {

    suspend fun getUser(): User?

    suspend fun signInWithFirebase(email: String, password: String): AuthResult

    suspend fun signUpWithFirebase(
        email: String,
        name: String,
        password: String
    ): AuthResult

    suspend fun sendResetEmail(email: String): AuthResult

    suspend fun resetPassword(password: String): AuthResult

    fun signOut(): AuthResult

}