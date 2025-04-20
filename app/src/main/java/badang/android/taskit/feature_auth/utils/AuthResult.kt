package badang.android.taskit.feature_auth.utils

import android.view.View
import badang.android.taskit.R
import badang.android.taskit.common.UiHelper
import java.lang.Exception

sealed class AuthResult {
    data object None: AuthResult()
    data class Success(val message: String? = null): AuthResult()
    data class Failure(val exception: Exception?): AuthResult()

}

fun AuthResult.showUiSnackBar(view: View){
    when (this){
        is AuthResult.Failure -> UiHelper.showSnackBar(view, R.string.auth_sucess)
        AuthResult.None -> Unit
        is AuthResult.Success -> UiHelper.showSnackBar(view,R.string.auth_failure)
    }
}
