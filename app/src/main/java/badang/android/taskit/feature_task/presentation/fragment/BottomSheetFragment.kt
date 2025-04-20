package badang.android.taskit.feature_task.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentBottomSheetBinding
import badang.android.taskit.feature_task.presentation.TaskEvent
import badang.android.taskit.feature_task.presentation.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: TaskViewModel by hiltNavGraphViewModels(R.id.nav_graph)
        val onEvent: (TaskEvent)->Unit = viewModel::onEvent
        val taskState = viewModel.tasks.value
        val isChecked = requireArguments().getBoolean("IS_CHECKED")
        val isAlarmed = requireArguments().getBoolean("IS_ALARMED")
        val taskId = requireArguments().getString("TASK_ID", "")

        iconEdit = binding.txvDialogEdit
        iconCheck = binding.txvDialogCheck
        iconAlarm = binding.txvDialogAlarm
        iconDelete = binding.txvDialogDelete
        iconDeleteAll = binding.txvDialogDeleteAll

        iconCheck.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (isChecked) R.drawable.ic_modal_unchecked
            else R.drawable.ic_modal_check
            ,0,0,0
        )

        iconCheck.setText(
            if (isChecked) R.string.modal_incomplete_task else R.string.modal_complete_task
        )
        iconAlarm.setText(
            if (isAlarmed) R.string.modal_alarm_off else R.string.modal_set_alarm
        )

        iconAlarm.setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (isAlarmed) R.drawable.ic_modal_alarm_off
            else R.drawable.ic_modal_alarm
            ,0,0,0
        )

        iconAlarm.setOnClickListener {
            if (isAlarmed)
                onEvent(TaskEvent.CancelNotification(taskId))
            else
                onEvent(TaskEvent.SetNotification(taskId))

            dismiss()
        }

        iconEdit.setOnClickListener {
            findNavController().navigate(R.id.action_bottomSheetFragment_to_upsertFragment)
            dismiss()
        }

        iconCheck.setOnClickListener {
            if (taskState.id == null) {
                Toast.makeText(requireContext(), "NULL _ ID", Toast.LENGTH_SHORT).show()
            } else {
                onEvent(
                    TaskEvent.ToggleComplete(taskState.id)
                )
            }
            dismiss()
        }

        iconDelete.setOnClickListener {
            showAlertDialog(
                onDeleteEvent = { onEvent(TaskEvent.DeleteAll) },
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
        dismiss()
    }

}