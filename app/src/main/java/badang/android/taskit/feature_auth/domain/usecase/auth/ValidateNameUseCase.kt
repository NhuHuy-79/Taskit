package badang.android.taskit.feature_auth.domain.usecase.auth

class ValidateNameUseCase {
    operator fun invoke(name: String): Int {
        val nameRegex = "^[\\p{L} .'-]+$".toRegex()
        return when{
            name.isEmpty() -> EMPTY_NAME
            !nameRegex.matches(name) -> INVALID_NAME
            else -> VALID_NAME
        }
    }
}

const val EMPTY_NAME = 0
const val VALID_NAME = 1
const val INVALID_NAME = -1