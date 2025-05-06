package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentSignInBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.helper.AuthFragment
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : AuthFragment(){

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel(
            view = view,
            onNavigation = { findNavController().popBackStack() }
        )

        signInOnListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            viewModel.onEvent(AuthEvent.SignIn(email, password))
        }

        navigateToSignUp()
        navigateToDashBoard()
        navigateToForgotPasswd()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun signInOnListener(
        onEvent: (AuthEvent)->Unit
    ){

        binding.btnSignIn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            viewModel.onClear()
            onEvent(AuthEvent.SignIn(email, password))
        }

    }

    private fun navigateToSignUp(){
        binding.txvLinkToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun navigateToDashBoard(){
        binding.btnNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToForgotPasswd(){
        binding.txvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_sendEmailSheet)
        }
    }
}