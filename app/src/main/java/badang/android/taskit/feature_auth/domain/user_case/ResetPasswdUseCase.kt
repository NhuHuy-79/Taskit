package badang.android.taskit.feature_auth.domain.user_case

import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.domain.model.AuthResult
import javax.inject.Inject

class ResetPasswdUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(passwd: String, confirm: String): AuthResult {
        return if (passwd != confirm) {
            repository.changePassword(passwd)
        } else {
            AuthResult.Failure(
                AuthResult.Error.EMPTY_INPUT
            )
        }
    }
}