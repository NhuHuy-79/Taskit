package badang.android.taskit.feature_auth.domain.repository

import badang.android.taskit.feature_auth.domain.model.AuthResult
import badang.android.taskit.feature_auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun getUser(): Flow<User?>

    suspend fun login(email: String, password: String): AuthResult

    suspend fun register(
        email: String,
        name: String,
        password: String,
        confirm: String,
    ): AuthResult

    suspend fun sendResetEmail(email: String): AuthResult

    suspend fun changePassword(password: String): AuthResult

    suspend fun signOut(): AuthResult

}