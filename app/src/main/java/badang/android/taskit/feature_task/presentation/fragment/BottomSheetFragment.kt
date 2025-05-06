package badang.android.taskit.feature_task.presentation.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.BundleCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.core.AppHelper
import badang.android.taskit.databinding.FragmentBottomSheetBinding
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.presentation.TaskViewModel
import badang.android.taskit.feature_task.presentation.other.TaskEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment: BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var iconEdit: TextView
    private lateinit var iconCheck: TextView
    private lateinit var iconAlarm: TextView
    private lateinit var iconDelete: TextView
    private lateinit var iconDeleteAll: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(requireContext(), R.color.color_hovered)
            )
        )
        dialog?.window?.setDimAmount(0F)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: TaskViewModel by hiltNavGraphViewModels(R.id.nav_graph)
        val onEvent: (TaskEvent)->Unit = viewModel::onEvent
        val taskState = viewModel.tasks.value

        val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           requireArguments().getParcelable("task", Task::class.java)
        } else {
            BundleCompat.getParcelable(requireArguments(), "task", Task::class.java)
        }

        iconEdit = binding.txvDialogEdit
        iconCheck = binding.txvDialogCheck
        iconAlarm = binding.txvDialogAlarm
        iconDelete = binding.txvDialogDelete
        iconDeleteAll = binding.txvDialogDeleteAll

        iconCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (task?.isDone == true) R.drawable.ic_modal_unchecked
            else R.drawable.ic_modal_check
            ,0,0,0
        )

        iconCheck.setText(
            if (task?.isDone == true ) R.string.modal_incomplete_task else R.string.modal_complete_task
        )
        iconAlarm.setText(
            if (task?.isEnabled == true) R.string.modal_alarm_off else R.string.modal_set_alarm
        )

        iconAlarm.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (task?.isEnabled == true) R.drawable.ic_modal_alarm_off
            else R.drawable.ic_modal_alarm
            ,0,0,0
        )

        iconAlarm.setOnClickListener {

            dismiss()
        }

        iconEdit.setOnClickListener {
            task?.let {
                viewModel.updateTaskState(task)
            }
            findNavController().navigate(R.id.action_bottomSheetFragment_to_upsertFragment)
            dismiss()
        }

        iconCheck.setOnClickListener {
            if (taskState.id == null) {
               AppHelper.showToast(requireContext(), R.string.callback_failed)
            } else {
                onEvent(
                    TaskEvent.ToggleComplete(taskState.id)
                )
            }
            dismiss()
        }

        iconDelete.setOnClickListener {
            showAlertDialog(
                onDeleteEvent = { onEvent(TaskEvent.Delete(task!!)) },
                title = R.string.dialog_delete_title,
                message = R.string.dialog_delete_content
            )
        }

        iconDeleteAll.setOnClickListener {
            showAlertDialog(
                onDeleteEvent = { onEvent(TaskEvent.DeleteAll) },
                title = R.string.dialog_delete_all_title,
                message = R.string.dialog_delete_all_content,
            )
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAlertDialog(
        onDeleteEvent: ()-> Unit,
        title: Int,
        message: Int
        ){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
               R.string.dialog_positive_btn
            ) { dialog, _ ->
                onDeleteEvent()
                dialog.cancel()
                dismiss()
                return@setPositiveButton
            }
            .setNegativeButton(
                R.string.dialog_negative_btn
            ) { dialog, _ ->
                onDeleteEvent()
                dialog.cancel()
                dismiss()
                return@setNegativeButton
            }

        dialog.show()
    }

}