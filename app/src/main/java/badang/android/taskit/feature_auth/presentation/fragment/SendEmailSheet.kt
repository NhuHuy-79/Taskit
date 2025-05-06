package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentForgotPasswordBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SendEmailSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendEmailListener()
    }

    private fun sendEmailListener(){
        binding.btnSignIn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            viewModel.onEvent(
                AuthEvent.SendResetEmail(email)
            )
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}