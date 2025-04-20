package badang.android.taskit.feature_auth.domain.usecase.auth

import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.utils.AuthResult
import javax.inject.Inject

class ResetPasswdUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(passwd: String): AuthResult{
        return repository.resetPassword(passwd)
    }
}