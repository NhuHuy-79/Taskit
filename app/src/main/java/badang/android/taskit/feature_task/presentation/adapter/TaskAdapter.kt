package badang.android.taskit.feature_task.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import badang.android.taskit.R
import badang.android.taskit.databinding.TaskItemBinding
import badang.android.taskit.feature_task.utils.AlarmUtils
import badang.android.taskit.feature_task.domain.model.Task


class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFFERCALLBACK) {

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

                if (task.time == -1L){
                    taskTime.visibility = View.INVISIBLE
                }

                checkbox.isChecked = task.isDone
                taskContent.text = task.content
                taskContent.setTextColor(textColor)
                taskTime.visibility = if (task.isDone) View.INVISIBLE else View.VISIBLE
                taskTime.setTextColor(ContextCompat.getColor(root.context, R.color.white))
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
        val DIFFERCALLBACK = object : DiffUtil.ItemCallback<Task>() {
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