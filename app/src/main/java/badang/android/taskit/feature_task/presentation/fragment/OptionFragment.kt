package badang.android.taskit.feature_task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.core.Constant
import badang.android.taskit.databinding.FragmentOptionBinding
import badang.android.taskit.feature_auth.presentation.AuthEvent
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOptionBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isUserLogged = Bundle().getBoolean(Constant.Extra.IS_LOGGED)

        if (isUserLogged) {
            updateUIWithUser()
        } else {
            updateUIWithoutUser()
        }
    }

    private fun updateUIWithUser(){
        binding.txvDialogSignOut.visibility = View.VISIBLE
        binding.txvDialogChangePassword.visibility = View.VISIBLE
        binding.txvDialogLogin.visibility = View.GONE
        binding.txvDialogRegister.visibility = View.GONE

        binding.txvDialogSignOut.setOnClickListener {
            viewModel.onEvent(
                AuthEvent.SignOut
            )
            dialog?.dismiss()
        }

        binding.txvDialogChangePassword.setOnClickListener {
            findNavController().navigate(R.id.action_optionFragment_to_resetPasswordFragment)
            dialog?.dismiss()
        }

        binding.txvDialogAboutUs.setOnClickListener {
            navigateToOurInfo()
            dialog?.dismiss()
        }
    }

    private fun updateUIWithoutUser(){
        binding.txvDialogLogin.visibility = View.VISIBLE
        binding.txvDialogRegister.visibility = View.VISIBLE

        binding.txvDialogLogin.setOnClickListener {
            findNavController().navigate(R.id.action_optionFragment_to_signInFragment)
            dialog?.dismiss()
        }

        binding.txvDialogRegister.setOnClickListener {
            findNavController().navigate(R.id.action_optionFragment_to_signUpFragment)
            dialog?.dismiss()
        }

        binding.txvDialogAboutUs.setOnClickListener {
            navigateToOurInfo()
            dialog?.dismiss()
        }

        binding.txvDialogSignOut.visibility = View.GONE
        binding.txvDialogChangePassword.visibility = View.GONE
    }

    private fun navigateToOurInfo(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}