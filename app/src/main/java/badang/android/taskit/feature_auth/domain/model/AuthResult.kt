package badang.android.taskit.feature_auth.domain.model


sealed class AuthResult {

    data object Success: AuthResult()

    class Failure(val error: Error): AuthResult()

    enum class Error {
        UNMATCHED_PASSWORDS,
        INVALID_EMAIL,
        WEAK_PASSWORD,
        INVALID_PASSWORD_LENGTH,
        USER_ALREADY_EXISTS,
        INCORRECT_EMAIL_OR_PASSWORD,
        UNDEFINED_ERROR,
        EMPTY_INPUT,
        USER_NOT_EXIST,
        NETWORK_ERROR,
        TIME_OUT,
    }
}