package badang.android.taskit.feature_auth.presentation


sealed class AuthState {

    data object Success : AuthState()

    data object Nothing : AuthState()

    data object Loading : AuthState()

}

sealed class ErrorState : AuthState(){
    data object UnmatchedPasswords : ErrorState()
    data object InvalidEmail : ErrorState()
    data object WeakPassword : ErrorState()
    data object InvalidPasswordLength : ErrorState()
    data object UserAlreadyExists : ErrorState()
    data object IncorrectEmailOrPassword : ErrorState()
    data object UndefinedError : ErrorState()
    data object EmptyInput: ErrorState()
    data object NetworkError: ErrorState()
    data object UserNotExist: ErrorState()
    data object TimeOut: ErrorState(

    )
}


