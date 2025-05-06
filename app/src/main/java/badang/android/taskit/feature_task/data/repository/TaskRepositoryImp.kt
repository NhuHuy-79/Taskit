package badang.android.taskit.feature_task.data.repository

import badang.android.taskit.core.Constant
import badang.android.taskit.feature_task.data.local.data_store.DataPreferences
import badang.android.taskit.feature_task.data.local.room.TaskDao
import badang.android.taskit.feature_task.data.local.room.TaskEntity
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toEntity
import badang.android.taskit.feature_task.data.mapper.TaskMapper.Companion.toNetWork
import badang.android.taskit.feature_task.domain.model.Task
import badang.android.taskit.feature_task.domain.repository.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await


class TaskRepositoryImp(
    private val auth: FirebaseAuth,
    private val taskDao: TaskDao,
    private val db: FirebaseFirestore
): TaskRepository {


    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    override suspend fun upsertTask(task: TaskEntity) {
        taskDao.upsertTask(task)
        auth.currentUser?.let { user ->
            val taskRef = db.collection(Constant.FireStore.COLLECTION_USER)
                .document(user.uid)
                .collection(Constant.FireStore.COLLECTION_TASK)
                .document(task.id)

            try {
                taskRef.set(task.toNetWork(), SetOptions.merge()).await()
                markTaskSynced(task.id)
            } catch (e: FirebaseFirestoreException) {
                markTaskSynced(task.id)
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun markTaskSynced(id: String) {
        return taskDao.markTaskSynced(id)
    }

    override suspend fun markTaskUnSynced(id: String) {
        return taskDao.markTaskUnSynced(id)
    }

    override suspend fun setAlarm(id: String) {
        return taskDao.setAlarm(id)
    }


    override suspend fun toggleCompletingTask(id: String) {
        taskDao.toggleCompletingTask(id)
        val task = taskDao.getTaskById(id)
        auth.currentUser?.let { user ->
            try {
                db.collection(Constant.FireStore.COLLECTION_USER)
                    .document(user.uid)
                    .collection(Constant.FireStore.COLLECTION_TASK)
                    .document(id)
                    .update("isDone", task?.isDone ?: false)
                    .await()
            } catch (e: FirebaseFirestoreException) {
                markTaskSynced(id)
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteTask(id: String) {
        taskDao.deleteTask(id)
        auth.currentUser?.let { user ->
            try {
                db.collection(Constant.FireStore.COLLECTION_USER)
                    .document(user.uid)
                    .collection(Constant.FireStore.COLLECTION_TASK)
                    .document(id)
                    .update("isDeleted", true)
                    .await()
            } catch (e: FirebaseFirestoreException) {
                markTaskSynced(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
        auth.currentUser?.let { user ->
            val docRef = db.collection(Constant.FireStore.COLLECTION_USER)
                .document(user.uid)
                .collection(Constant.FireStore.COLLECTION_TASK)

            try {
                val snapshot = docRef.get().await()
                snapshot.documents.forEach { document ->
                    docRef.document(document.id)
                        .update("isDeleted", true)
                        .await()
                }
            } catch (e: Exception) {
                //Sync all task
            }
        }
    }

    override suspend fun deleteAllTasks(taskList: List<Task>) {
        taskList.forEach { task->
            val taskEntity = task.toEntity()
            taskDao.deleteTask(task.toEntity().id)
            auth.currentUser?.let { user ->
                val docRef = db.collection(Constant.FireStore.COLLECTION_USER)
                    .document(user.uid)
                    .collection(Constant.FireStore.COLLECTION_TASK)
                    .document(taskEntity.id)
                try {
                    docRef.update("isDeleted", true)
                        .await()
                } catch (e: Exception) {
                   markTaskSynced(taskEntity.id)
                }
            }
        }
    }

    override suspend fun getTaskToSync(): List<TaskEntity> {
        return taskDao.getTaskToSync()
    }

    override suspend fun getTask(id: String): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    override suspend fun syncTask(list: List<TaskEntity>) {
       auth.currentUser?.let { user ->
           list.forEach { task->
               val docRef = db.collection(Constant.FireStore.COLLECTION_USER)
                   .document(user.uid)
                   .collection(Constant.FireStore.COLLECTION_TASK)
                   .document(task.id)
               try {
                   docRef.set(task.toNetWork())
                   taskDao.markTaskUnSynced(task.id)
               } catch(e: Exception){

               }
           }
       }
    }
}