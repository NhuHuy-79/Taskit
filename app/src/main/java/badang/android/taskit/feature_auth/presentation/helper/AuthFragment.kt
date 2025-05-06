package badang.android.taskit.feature_auth.presentation.helper

import android.app.Dialog
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import badang.android.taskit.R
import badang.android.taskit.core.AppHelper
import badang.android.taskit.feature_auth.presentation.AuthState
import badang.android.taskit.feature_auth.presentation.ErrorState
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


abstract class AuthFragment : Fragment() {

    abstract val viewModel: AuthViewModel

    private var loadingDialog: Dialog? = null

    protected fun observeViewModel(
        view: View,
        onNavigation: ()-> Unit,
    ) {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authState.collect { state ->
                    when (state) {
                        AuthState.Loading -> {
                            showLoadingProgressBar()
                        }

                        AuthState.Nothing -> Unit
                        AuthState.Success -> {
                            closeLoadingProgressBar()
                            AppHelper.showSnackBar(requireContext(),view, "Successfully!")
                            viewModel.onClear()
                            onNavigation()
                        }

                        is ErrorState -> {
                            closeLoadingProgressBar()
                            val message = when (state) {
                                ErrorState.IncorrectEmailOrPassword -> "Email or Password is incorrect!"
                                ErrorState.InvalidEmail -> "Invalid Email!"
                                ErrorState.InvalidPasswordLength -> "Password is at least 8 characters"
                                ErrorState.UndefinedError -> "Undefined error occurred"
                                ErrorState.UnmatchedPasswords -> "Password is not matched!"
                                ErrorState.UserAlreadyExists -> "User already exists!"
                                ErrorState.WeakPassword -> "Password must contain uppercase, lowercase and special characters"
                                ErrorState.EmptyInput -> "Empty Input"
                                ErrorState.NetworkError -> "No connection"
                                ErrorState.UserNotExist -> "User does not exist!"
                                ErrorState.TimeOut -> "Time Out!"
                            }
                            AppHelper.showSnackBar(requireContext(), view, message)
                            viewModel.onClear()
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingProgressBar(){
        loadingDialog = Dialog(requireContext()).apply {
            setContentView(R.layout.progress_bar_indicator)
            setCancelable(false)
        }
        loadingDialog?.show()
    }

    private fun closeLoadingProgressBar(){
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}