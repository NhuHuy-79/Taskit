package badang.android.taskit.feature_task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import badang.android.taskit.R
import badang.android.taskit.core.AppHelper
import badang.android.taskit.core.Constant
import badang.android.taskit.databinding.FragmentDashboardBinding
import badang.android.taskit.feature_auth.presentation.viewmodel.AuthViewModel
import badang.android.taskit.feature_task.presentation.TaskAdapter
import badang.android.taskit.feature_task.presentation.TaskViewModel
import badang.android.taskit.feature_task.presentation.other.TaskEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class
DashBoardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter

    private val taskViewModel: TaskViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val authViewModel: AuthViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateAvatarWithState()
        createAdapterWithState()
        navigateToUpsert()
        openOptionDialog()

    }

    private fun openOptionDialog() {
        val isUserLogged = authViewModel.userState.value != null
        val bundle = Bundle().apply {
            putBoolean(Constant.Extra.IS_LOGGED, isUserLogged )
        }
        binding.imgAvt.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_optionFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToUpsert(){
        binding.horizontalButtons.btnAddTodo.setOnClickListener {
            taskViewModel.resetState()
            findNavController().navigate(R.id.action_dashBoardFragment_to_upsertFragment)
        }
    }



    private fun createAdapterWithState(){
        taskAdapter = TaskAdapter()
        taskAdapter.clickListener = { task ->
            task.id?.let { id->
                taskViewModel.onEvent(TaskEvent.ToggleComplete(id))
            }

        }

        taskAdapter.longClickListener = { task,_->
            val bundle = Bundle().apply {
                putParcelable("task", task)
            }
            AppHelper.printLog("ID", task.id ?: "null")
            taskViewModel.updateTaskState(task)
            findNavController().navigate(R.id.action_dashBoardFragment_to_bottomSheetFragment, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                taskViewModel.tasks.collect{ state ->
                    taskAdapter.submitList(state.tasks)
                }

            }
        }

        binding.rcvUndoneTodo.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



    }

    private fun updateAvatarWithState(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                authViewModel.userState.collect{ user ->
                    if (user == null) {
                        updateImage(R.drawable.img_unlogged_user)
                    } else {
                        updateImage(R.drawable.img_avt)
                    }
                }
            }
        }
    }

    private fun updateImage(@DrawableRes res: Int){
        binding.imgAvt.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), res)
        )
    }


}
