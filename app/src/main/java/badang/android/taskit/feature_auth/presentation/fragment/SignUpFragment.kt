package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentSignUpBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.helper.AuthFragment
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: AuthFragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel(view) { findNavController().popBackStack() }
        signUpOnListener()
        linkToSignIn()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun signUpOnListener() {

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtFullName.text.toString()
            val confirm = binding.edtConfirmPassword.text.toString().trim()

            viewModel.onEvent(AuthEvent.SignUp(email, password, name, confirm))
        }
    }

    private fun linkToSignIn(){
        binding.btnNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.txvLinkToSignIn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}