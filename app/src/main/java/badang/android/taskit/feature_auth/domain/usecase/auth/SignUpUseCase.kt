package badang.android.taskit.feature_auth.domain.usecase.auth

import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.utils.AuthResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, name: String, password:String) : AuthResult{
        return repository.signUpWithFirebase(email, name, password)
    }
}