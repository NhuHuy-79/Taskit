package badang.android.taskit.feature_auth.domain.usecase.auth

class ValidatePasswordUseCase {
    operator fun invoke(password: String, confirm: String): Int{
        return when {
            (password != confirm) -> NOT_MATCH
            (password.length < 8) -> INVALID_LENGTH
            (password.isEmpty() || confirm.isEmpty()) -> EMPTY_PASSWD
            else -> VALID_PASSWD
        }
    }
}

const val EMPTY_PASSWD = 0
const val INVALID_LENGTH = 1
const val NOT_MATCH = -1
const val VALID_PASSWD = 2