/*
package badang.android.taskit.feature_auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import badang.android.taskit.feature_auth.domain.model.User
import badang.android.taskit.feature_auth.domain.usecase.auth.ResetPasswdUseCase
import badang.android.taskit.feature_auth.domain.usecase.auth.SendResetEmailUseCase
import badang.android.taskit.feature_auth.domain.usecase.auth.SignInUseCase
import badang.android.taskit.feature_auth.domain.usecase.auth.SignOutUseCase
import badang.android.taskit.feature_auth.domain.usecase.auth.SignUpUseCase
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.AuthState
import badang.android.taskit.feature_auth.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val resetPasswdUseCase: ResetPasswdUseCase,
    private val sendResetEmail: SendResetEmailUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun onEvent(event: AuthEvent){
        when (event){
            is AuthEvent.ResetPassword -> viewModelScope.launch {
                val result = resetPasswdUseCase(event.password)
                updateAuthResult(result)
            }

            is AuthEvent.SignIn -> viewModelScope.launch {
                val result = signInUseCase(event.email, event.password)
                updateAuthResult(result)
            }

            AuthEvent.SignOut -> viewModelScope.launch {
                val result = signOutUseCase()
                updateAuthResult(result)
            }

            is AuthEvent.SignUp -> viewModelScope.launch{
                val result = signUpUseCase(event.email, event.name, event.password)
                updateAuthResult(result)
            }

            is AuthEvent.SendResetEmail -> viewModelScope.launch {
                val result = sendResetEmail(event.email)
                updateAuthResult(result)
            }
        }
    }

    fun getUser(user: User) {
        _authState.update {
            it.copy(user = user)
        }
    }

    private fun updateAuthResult(result: AuthResult){
        _authState.update {
            it.copy(result = result)
        }
    }

}
*/
