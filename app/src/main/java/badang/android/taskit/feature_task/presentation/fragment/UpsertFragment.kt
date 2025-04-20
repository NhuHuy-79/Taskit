package badang.android.taskit.feature_task.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import badang.android.taskit.R
import badang.android.taskit.databinding.FragmentUpsertBinding
import badang.android.taskit.feature_task.presentation.TaskEvent
import badang.android.taskit.feature_task.presentation.TaskState
import badang.android.taskit.feature_task.presentation.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


const val EMPTY_ERROR_TAG = "Input is empty!"
const val SUCCESS_TAG = "Successfully"
const val DATE_TAG = "TASKIT TIME PICKER"
const val SNACK_BAR_DURATION = 500

@AndroidEntryPoint
class UpsertFragment : Fragment() {
    private lateinit var binding: FragmentUpsertBinding
    private lateinit var btnNavigateBack: ImageView
    private lateinit var btnAddToList: Button
    private lateinit var inputContentLayout: TextInputLayout
    private lateinit var inputContent: EditText

    private var taskState: TaskState = TaskState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: TaskViewModel by hiltNavGraphViewModels(R.id.nav_graph)
        val onEvent: (TaskEvent)->Unit = viewModel::onEvent
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.tasks.collect { state ->
                    taskState = state
                }
            }
        }

        btnNavigateBack = binding.btnNavigateBack
        btnNavigateBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        inputContentLayout = binding.edtContentLayout
        inputContentLayout.setEndIconOnClickListener {
            showTimerPicker(childFragmentManager, viewModel::onEvent)
        }

        inputContent = binding.edtContent
        inputContent.setHint(taskState.content)
        inputContent.post {
            inputContent.requestFocus()
            val ime = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ime.showSoftInput(inputContent, InputMethodManager.SHOW_IMPLICIT)
        }
        inputContent.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                onAddTaskListener(
                    view = view,
                    onEvent = onEvent,
                    value = taskState.content,
                    hour = taskState.hour,
                    minute = taskState.minute,
                    id = taskState.id
                )
                true
            } else
                false
        }
        inputContent.addTextChangedListener(
            afterTextChanged = { value ->
                viewModel.onEvent(TaskEvent.OnTextChange(value.toString()))
                Log.d("TEXT_INPUT", value.toString())
            }
        )

        btnAddToList = binding.btnAddToList
        btnAddToList.setOnClickListener {

            onAddTaskListener(
                view = view,
                onEvent = onEvent,
                value = taskState.content,
                hour = taskState.hour,
                minute = taskState.minute,
                id = taskState.id
            )

        }
    }

    private fun onAddTaskListener(
        view: View,
        onEvent: (TaskEvent)->Unit,
        value: String,
        hour: Int,
        minute: Int,
        id: String?,
    ){
        if (value.isEmpty()){
            Snackbar.make(
                view, EMPTY_ERROR_TAG, SNACK_BAR_DURATION
            ).show()
        } else {
            onEvent(
                TaskEvent.Upsert(
                id = id,
                content = value,
                hour = hour,
                minute = minute,
            ))
            Snackbar.make(
                view, SUCCESS_TAG, SNACK_BAR_DURATION
            ).show()
            findNavController().popBackStack()
        }
    }

    private fun showTimerPicker(
        fragmentManager: FragmentManager,
        onEvent: (TaskEvent) -> Unit
    ){
        val timer = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(6)
            .setTheme(R.style.TimePicker)
            .setMinute(45)
            .setTitleText(R.string.picker_title)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()

        timer.addOnPositiveButtonClickListener {
            onEvent(TaskEvent.OnAlarmSet(timer.hour, timer.minute))
        }
        timer.addOnNegativeButtonClickListener {

        }
        timer.show(fragmentManager, DATE_TAG)
    }

}