package badang.android.taskit.feature_auth.presentation

sealed class AuthEvent {
    data object SignOut: AuthEvent()
    data class SignIn(val email: String, val password: String): AuthEvent()
    data class SignUp(val email: String, val password: String, val name: String): AuthEvent()
    data class ResetPassword(val password: String): AuthEvent()
    data class SendResetEmail(val email: String): AuthEvent()
}