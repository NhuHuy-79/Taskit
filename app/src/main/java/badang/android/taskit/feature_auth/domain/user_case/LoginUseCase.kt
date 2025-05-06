package badang.android.taskit.feature_auth.domain.user_case

import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.domain.model.AuthResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password:String) : AuthResult {
        return repository.login(email, password)
    }
}