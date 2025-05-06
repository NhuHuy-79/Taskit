package badang.android.taskit.feature_auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import badang.android.taskit.feature_auth.domain.model.AuthResult
import badang.android.taskit.feature_auth.domain.user_case.CheckUserUseCase
import badang.android.taskit.feature_auth.domain.user_case.LoginUseCase
import badang.android.taskit.feature_auth.domain.user_case.RegisterUseCase
import badang.android.taskit.feature_auth.domain.user_case.ResetPasswdUseCase
import badang.android.taskit.feature_auth.domain.user_case.SendResetEmailUseCase
import badang.android.taskit.feature_auth.domain.user_case.SignOutUseCase
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.AuthState
import badang.android.taskit.feature_auth.presentation.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val resetPasswdUseCase: ResetPasswdUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val sendResetEmailUseCase: SendResetEmailUseCase,
    private val checkUserUseCase: CheckUserUseCase
): ViewModel()  {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Nothing)
    val authState = _authState.asStateFlow()

    val userState = checkUserUseCase().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.ResetPassword -> viewModelScope.launch {
                onLoading()
                val result = resetPasswdUseCase(
                    passwd = event.password,
                    confirm = event.confirm
                )
                onUpdate(result)
            }

            is AuthEvent.SendResetEmail -> viewModelScope.launch {
                onLoading()
                val result = sendResetEmailUseCase(event.email)
                onUpdate(result)
            }

            is AuthEvent.SignIn -> viewModelScope.launch {
                onLoading()
                val result = loginUseCase(event.email, event.password)
                onUpdate(result)
            }

            AuthEvent.SignOut -> viewModelScope.launch{
                val result = signOutUseCase()
                onUpdate(result)
            }

            is AuthEvent.SignUp -> viewModelScope.launch {
                onLoading()
                val result = registerUseCase(
                    email = event.email,
                    name = event.name,
                    password = event.password,
                    confirm = event.confirm

                )
                onUpdate(result)
            }
        }
    }

    private fun onUpdate(result: AuthResult){
        when (result) {
            AuthResult.Success -> _authState.value = AuthState.Success
            is AuthResult.Failure -> {
                _authState.value = when (result.error) {
                    AuthResult.Error.UNMATCHED_PASSWORDS -> ErrorState.UnmatchedPasswords
                    AuthResult.Error.INVALID_EMAIL -> ErrorState.InvalidEmail
                    AuthResult.Error.WEAK_PASSWORD -> ErrorState.WeakPassword
                    AuthResult.Error.INVALID_PASSWORD_LENGTH -> ErrorState.InvalidPasswordLength
                    AuthResult.Error.USER_ALREADY_EXISTS -> ErrorState.UserAlreadyExists
                    AuthResult.Error.INCORRECT_EMAIL_OR_PASSWORD -> ErrorState.IncorrectEmailOrPassword
                    AuthResult.Error.UNDEFINED_ERROR -> ErrorState.UndefinedError
                    AuthResult.Error.EMPTY_INPUT -> ErrorState.EmptyInput
                    AuthResult.Error.USER_NOT_EXIST -> ErrorState.UserNotExist
                    AuthResult.Error.NETWORK_ERROR -> ErrorState.NetworkError
                    AuthResult.Error.TIME_OUT -> ErrorState.TimeOut
                }
            }

        }
    }

    private suspend fun onLoading(){
        _authState.value = AuthState.Loading
        delay(2000L)
    }

    fun onClear(){
       _authState.value = AuthState.Nothing
    }
}