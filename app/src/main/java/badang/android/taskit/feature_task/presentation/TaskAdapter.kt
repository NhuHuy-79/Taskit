package badang.android.taskit.feature_task.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import badang.android.taskit.R
import badang.android.taskit.databinding.TaskItemBinding
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.utils.AlarmUtils


class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(differCallback) {

    var clickListener: ((Task) -> Unit)? = null
    var longClickListener: ((Task,View)->Unit)? = null

    class TaskViewHolder(
        private val binding: TaskItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                val textColor = if (task.isDone) {
                    ContextCompat.getColor(root.context, R.color.color_muted_text)
                } else {
                    ContextCompat.getColor(root.context, R.color.black)
                }

                checkbox.isChecked = task.isDone
                taskContent.text = task.content
                taskContent.setTextColor(textColor)
                if (task.time == null) {
                    taskTime.background = null
                }
                taskTime.setTextColor(
                    ContextCompat.getColor(root.context, R.color.white)
                )

                taskTime.text = AlarmUtils.convertToString(
                   alarmTime = task.time
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false )

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
        holder.itemView.setOnClickListener {
            clickListener?.invoke(task)
        }

        holder.itemView.setOnLongClickListener { view->
            longClickListener?.invoke(task, view)
            true
        }
    }


    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem.content == newItem.content
            }

            override fun areContentsTheSame(
                oldItem: Task,
                newItem: Task
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}