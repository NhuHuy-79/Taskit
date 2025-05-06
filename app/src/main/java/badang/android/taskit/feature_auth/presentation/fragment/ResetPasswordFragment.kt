package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentResetPasswordBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.helper.AuthFragment
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel

class ResetPasswordFragment : AuthFragment(){
    override val viewModel: AuthViewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph)

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNavigateBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observeViewModel(
            view = view,
            onNavigation = { findNavController().popBackStack() }
        )

        resetPasswordListener()
    }

    private fun resetPasswordListener() {
        binding.btnResetPassword.setOnClickListener {
            val password = binding.passwordInput.text.toString().trim()
            val confirm = binding.confirmInput.text.toString().trim()
            viewModel.onEvent(
                AuthEvent.ResetPassword(password, confirm)
            )
        }
    }

}