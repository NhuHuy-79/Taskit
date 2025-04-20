package badang.android.taskit.feature_auth.domain.usecase.auth

import android.util.Patterns

class ValidateEmailUseCase {
    operator fun invoke (email: String) : Int {
        return when {
            email.isEmpty() -> EMPTY_EMAIL
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> INVALID_EMAIL
            else -> VALID_EMAIL
        }
    }
}

const val EMPTY_EMAIL = -1
const val INVALID_EMAIL = 0
const val VALID_EMAIL = 1
