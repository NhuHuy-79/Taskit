package badang.android.taskit.feature_auth.domain.user_case

import android.util.Patterns
import badang.android.taskit.core.AppHelper
import badang.android.taskit.feature_auth.domain.repository.AuthRepository
import badang.android.taskit.feature_auth.domain.model.AuthResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
        confirm: String
    ): AuthResult {
        validateEmail(email)?.let { error ->
            return AuthResult.Failure(error)
        }
        validatePasswords(password, confirm)?.let { error ->
            return AuthResult.Failure(error)
        }

        return repository.register(email, name, password, confirm)
    }

    private fun validatePasswords(password: String, repeatedPassword: String): AuthResult.Error? {
        if (password.isEmpty()) return AuthResult.Error.EMPTY_INPUT
        if (password != repeatedPassword) return AuthResult.Error.UNMATCHED_PASSWORDS
        if (password.length < MIN_PASSWORD_LENGTH) return AuthResult.Error.INVALID_PASSWORD_LENGTH
        if (!password.containsLettersAndDigits()) return AuthResult.Error.WEAK_PASSWORD

        return null
    }

    private fun validateEmail(email: String): AuthResult.Error? {
        if (email.isEmpty()) return AuthResult.Error.EMPTY_INPUT
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult.Error.INVALID_EMAIL
        }
        return null
    }


    private fun String.containsLettersAndDigits(): Boolean{
        var hasLetters = false
        var hasDigits = false

        for (symbol in this){
            if (symbol.isLetter()) hasLetters = true
            if (symbol.isDigit()) hasDigits = true
        }

        return (hasLetters && hasDigits)
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }
}