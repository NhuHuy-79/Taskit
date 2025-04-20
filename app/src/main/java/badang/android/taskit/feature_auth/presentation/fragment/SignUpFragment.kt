package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import badang.android.taskit.R
import badang.android.taskit.common.UiHelper
import badang.android.taskit.databinding.FragmentSignUpBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.AuthState
import badang.android.taskit.feature_auth.utils.AuthResult
import badang.android.taskit.feature_auth.utils.showUiSnackBar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment: Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var emailInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmInput: EditText

    private lateinit var signUpBtn: Button
    private lateinit var linkToSignIn: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var onEvent: (AuthEvent) -> Unit
    private lateinit var authState: AuthState

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



        emailInput = binding.edtEmail
        nameInput = binding.edtFullName
        passwordInput = binding.edtPassword
        confirmInput = binding.edtConfirmPassword
        signUpBtn = binding.btnSignUp
        linkToSignIn = binding.txvLinkToSignIn
        auth = FirebaseAuth.getInstance()


        linkToSignIn.setOnClickListener { navigateToSignIn(it) }

        signUpBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val passwd = passwordInput.text.toString()
            val confirm = confirmInput.text.toString()

            if (passwd != confirm) {
                UiHelper.showToast(requireContext(), 0)
                return@setOnClickListener
            }
            onEvent(AuthEvent.SignIn(email, passwd))
        }

    }

    private fun navigateToSignIn(view: View){
        view.findNavController().popBackStack()
    }

}