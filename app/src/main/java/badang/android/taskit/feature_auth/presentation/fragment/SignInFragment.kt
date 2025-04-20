package badang.android.taskit.feature_auth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import badang.android.taskit.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(){
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInBtn: Button
    private lateinit var linkToSignUp: TextView


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

        emailInput = binding.emailInput
        passwordInput = binding.passwdInput
        signInBtn = binding.btnSignIn
        linkToSignUp = binding.txvLinkToSignUp


    }


}