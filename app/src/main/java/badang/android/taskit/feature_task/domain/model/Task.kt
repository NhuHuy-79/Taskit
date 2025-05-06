package badang.android.taskit.feature_task.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: String? = null,
    val content: String,
    val time: Long? = null,
    val isDone: Boolean = false,
    val isEnabled: Boolean = true,
): Parcelable