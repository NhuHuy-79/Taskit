package badang.android.taskit.feature_auth.presentation

import badang.android.taskit.feature_auth.domain.model.User
import badang.android.taskit.feature_auth.utils.AuthResult

data class AuthState(
    val result: AuthResult = AuthResult.None,
    val user: User? = null
)