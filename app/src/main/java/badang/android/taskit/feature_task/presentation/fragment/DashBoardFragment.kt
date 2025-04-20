package badang.android.taskit.feature_task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentDashboardBinding
import badang.android.taskit.feature_task.presentation.adapter.TaskAdapter
import badang.android.taskit.feature_task.presentation.TaskEvent
import badang.android.taskit.feature_task.presentation.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter


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

        val viewModel: TaskViewModel by hiltNavGraphViewModels(R.id.nav_graph)
        val onEvent: (TaskEvent)->Unit = viewModel::onEvent

        taskAdapter = TaskAdapter()
        taskAdapter.clickListener = { task ->
            task.id?.let { id->
                onEvent(TaskEvent.ToggleComplete(id))
            }

        }

        taskAdapter.longClickListener = { task, view->
            val bundle = Bundle().apply {
                putBoolean("IS_CHECKED", task.isDone)
                putBoolean("IS_ALARMED", task.isAlarmed)
                putString("TASK_ID", task.id)
            }
            viewModel.updateTaskState(task)
            view.findNavController().navigate(R.id.action_dashBoardFragment_to_bottomSheetFragment, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.tasks.collect{ state ->
                    taskAdapter.submitList(state.tasks)
                }
            }
        }

        binding.imgAvt.setOnClickListener {

        }

        binding.rcvUndoneTodo.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        binding.horizontalButtons.btnAddTodo.setOnClickListener { view ->
            viewModel.resetTextField()
            view.findNavController().navigate(R.id.action_dashBoardFragment_to_upsertFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
